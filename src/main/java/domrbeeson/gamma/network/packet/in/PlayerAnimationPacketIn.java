package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerAnimation;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerAnimationPacketIn extends WorldPacketIn {

    private final int entityId;
    private final PlayerAnimation animation;

    public PlayerAnimationPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.PLAYER_ANIMATION, server, connection, stream);

        entityId = stream.readInt();
        animation = PlayerAnimation.getById(stream.readByte());
    }

    @Override
    public void handle() {
        Player player = getServer().getPlayerManager().get(getConnection());
        player.swingArm(); // TODO use animation
    }

}
