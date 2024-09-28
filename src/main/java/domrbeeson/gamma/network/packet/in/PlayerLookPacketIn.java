package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerLookPacketIn extends WorldPacketIn {

    private final float yaw, pitch;
    private final boolean flying;

    public PlayerLookPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.PLAYER_LOOK, server, connection, stream);

        yaw = stream.readFloat();
        pitch = stream.readFloat();
        flying = stream.readBoolean();
    }

    @Override
    public void handle() {
        Player player = getServer().getPlayerManager().get(getConnection());
        Pos currentPos = player.getPos();
        Pos newPos = new Pos(currentPos.x(), currentPos.y(), currentPos.z(), yaw, pitch);
        if (!player.getPos().equals(newPos)) {
            player.updatePos(newPos);
        }
    }

}
