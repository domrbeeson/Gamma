package domrbeeson.gamma.player;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.chat.ChatMessage;
import domrbeeson.gamma.event.events.ServerStopEvent;
import domrbeeson.gamma.network.SocketReader;
import domrbeeson.gamma.network.SocketWriter;
import domrbeeson.gamma.version.MinecraftVersion;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PlayerManager implements Closeable, Runnable {

    private final Map<String, Player> PLAYERS_BY_USERNAME = new HashMap<>();
    private final Map<PlayerConnection, Player> PLAYERS_BY_CONNECTION = new HashMap<>();
    private final MinecraftServer server;
    private final ServerSocket serverSocket;
    private final Thread thread;

    public PlayerManager(MinecraftServer server) {
        this.server = server;

        InetAddress host;
        ServerSocket serverSocket = null;
        try {
            host = InetAddress.getByName(MinecraftServer.SERVER_SETTINGS.getIP());
            serverSocket = new ServerSocket(MinecraftServer.SERVER_SETTINGS.getPort(), 0, host);
            serverSocket.setPerformancePreferences(1, 2, 0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            this.serverSocket = serverSocket;
        }

        server.listen(ServerStopEvent.class, event -> {
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread = Thread.startVirtualThread(this);
        thread.setName("ServerSocket");
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                if (socket == null) {
                    continue;
                }

                PlayerConnection connection = new PlayerConnection(socket);
                connection.setWriter(new SocketWriter(server, connection));
                connection.setReader(new SocketReader(server, connection));
            } catch (IOException e) {
                if (!serverSocket.isClosed()) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Player create(PlayerConnection connection, String username, MinecraftVersion version, @Nullable ChatMessage joinMessage) {
        Player player = PLAYERS_BY_USERNAME.get(username);
        if (player == null) {
            player = new Player(server, connection, version, username, server.getWorldManager().getDefaultWorld(), joinMessage);
            PLAYERS_BY_USERNAME.put(username, player);
            PLAYERS_BY_CONNECTION.put(connection, player);
            System.out.println(username + " joined [players: " + PLAYERS_BY_USERNAME.size() + "]");
        }
        return player;
    }

    public @Nullable Player get(String username) {
        return PLAYERS_BY_USERNAME.get(username);
    }

    public @Nullable Player get(PlayerConnection connection) {
        return PLAYERS_BY_CONNECTION.get(connection);
    }

    protected boolean remove(Player player) {
        PLAYERS_BY_USERNAME.remove(player.getUsername());
        return PLAYERS_BY_CONNECTION.remove(player.getConnection()) != null;
    }

    public boolean isOnline(Player player) {
        return isOnline(player.getUsername());
    }

    public boolean isOnline(String username) {
        return PLAYERS_BY_USERNAME.containsKey(username);
    }

    public Collection<Player> getPlayers() {
        return PLAYERS_BY_USERNAME.values();
    }

    @Override
    public void close() {
        thread.interrupt();
        List<Player> players = new ArrayList<>(getPlayers());
        players.forEach(Player::remove);
    }

    public int getPlayersOnline() {
        return PLAYERS_BY_USERNAME.size();
    }

}
