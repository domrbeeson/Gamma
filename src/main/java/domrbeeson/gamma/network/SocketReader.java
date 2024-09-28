package domrbeeson.gamma.network;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.in.PacketIn;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.net.Socket;
import java.net.SocketException;

public class SocketReader implements Runnable {

    private final MinecraftServer server;
    private final PlayerConnection connection;
    private final Socket socket;
    private final Thread thread;

    public SocketReader(MinecraftServer server, PlayerConnection connection) {
        this.server = server;
        this.connection = connection;
        socket = connection.getSocket();
        thread = Thread.startVirtualThread(this);
    }

    @Override
    public void run() {
        DataInputStream stream;
        int packetId;
        try {
            while (socket.isConnected() && !socket.isClosed()) {
                stream = new DataInputStream(socket.getInputStream());
                packetId = stream.read();
                if (packetId == -1) {
                    kick("Received packet ID -1");
                    return;
                }
                PacketIn.PacketCreator func = PacketIn.getById(packetId);
                if (func == null) {
                    continue;
                }
                PacketIn packet = func.apply(server, connection, stream);
                packet.queue();
            }
        } catch (SocketException e) {
            kick(e.getMessage().substring(0, Math.min(e.getMessage().length(), 100)));
        } catch (Exception e) {
            kick(e.getMessage().substring(0, Math.min(e.getMessage().length(), 100)));
            e.printStackTrace();
        }
    }

    private void kick(String reason) {
        Player player = server.getPlayerManager().get(connection);
        if (player != null) {
            player.kick(reason);
        }
    }

    public void close() {
        thread.interrupt();
    }

}
