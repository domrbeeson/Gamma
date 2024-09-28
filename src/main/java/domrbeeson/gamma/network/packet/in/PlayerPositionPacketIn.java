package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerPositionPacketIn extends WorldPacketIn {

    private final double x, y, stance, z;
    private final boolean flying;

    public PlayerPositionPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.PLAYER_POSITION, server, connection, stream);

        x = stream.readDouble();
        y = stream.readDouble();
        stance = stream.readDouble();
        z = stream.readDouble();
        flying = stream.readBoolean();
    }

    @Override
    public void handle() {
        Player player = getServer().getPlayerManager().get(getConnection());
        Pos currentPos = player.getPos();
        Pos newPos = new Pos(x, y, z, currentPos.yaw(), currentPos.pitch());
        if (!player.getPos().equals(newPos)) {
            player.updatePos(newPos);
        }
    }

}
