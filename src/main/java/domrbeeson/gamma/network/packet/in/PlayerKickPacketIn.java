package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerKickPacketIn extends ServerPacketIn {

    private final String reason;

    public PlayerKickPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.PLAYER_KICK, server, connection, stream);

        Player player = server.getPlayerManager().get(connection);
        reason = readString(player.getProtocol(), stream);
    }

    @Override
    public void handle() {
        Player player = getServer().getPlayerManager().get(getConnection());
        player.remove();
    }

}
