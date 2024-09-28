package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.event.events.AsyncPlayerChatEvent;
import domrbeeson.gamma.event.events.PlayerCommandEvent;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.out.ChatPacketOut;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;
import domrbeeson.gamma.task.AsyncScheduledTask;

import java.io.DataInputStream;
import java.io.IOException;

public class ChatPacketIn extends AsyncPacketIn {

    private final String message;

    public ChatPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.CHAT, server, connection, stream);

        Player player = server.getPlayerManager().get(connection);
        message = readString(player.getProtocol(), stream);
    }

    @Override
    public void handle() {
        Player player = getServer().getPlayerManager().get(getConnection());
        if (message.startsWith("/")) {
            String[] parts = message.substring(1).split(" ");
            Command command = getServer().getCommandManager().get(parts[0]);
            String[] args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, parts.length - 1);
            PlayerCommandEvent event = new PlayerCommandEvent(player, command, args);
            getServer().call(event);
            if (event.isCancelled()) {
                return;
            }
            getServer().getScheduler().runNextTick(tick -> command.run(player, args));
        } else {
            AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(player, message);
            getServer().call(event);
            if (event.isCancelled()) {
                return;
            }

            getServer().getScheduler().scheduleTask(new AsyncChatTask(getServer(), player.getUsername(), message));
        }
    }

    private static class AsyncChatTask extends AsyncScheduledTask {
        private final MinecraftServer server;
        private final String username;
        private final String message;

        public AsyncChatTask(MinecraftServer server, String username, String message) {
            this.server = server;
            this.username = username;
            this.message = message;
        }

        @Override
        public void accept(Long tick) {
            // TODO support custom chat formatting
            ChatPacketOut packet = new ChatPacketOut("<" + username + "> " + message);
            for (Player player : server.getPlayerManager().getPlayers()) {
                player.sendPacket(packet);
            }
        }
    }
    
}
