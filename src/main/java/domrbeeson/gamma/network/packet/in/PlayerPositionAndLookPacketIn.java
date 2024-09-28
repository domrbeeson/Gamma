package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerPositionAndLookPacketIn extends WorldPacketIn {

    private final double x, y, stance, z;
    private final float yaw, pitch;
    private final boolean flying;

    public PlayerPositionAndLookPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.PLAYER_POSITION_AND_LOOK, server, connection, stream);

        x = stream.readDouble();
        y = stream.readDouble();
        stance = stream.readDouble();
        z = stream.readDouble();
        yaw = stream.readFloat();
        pitch = stream.readFloat();
        flying = stream.readBoolean();
    }

    @Override
    public void handle() {
        Player player = getServer().getPlayerManager().get(getConnection());
        Pos newPos = new Pos(x, y, z, yaw, pitch);
        if (!player.getPos().equals(newPos)) {
            player.updatePos(newPos);
        }
    }

}