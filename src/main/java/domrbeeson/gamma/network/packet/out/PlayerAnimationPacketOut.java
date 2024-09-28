package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerAnimation;

import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerAnimationPacketOut extends PacketOut {

    private final int entityId;
    private final PlayerAnimation animation;

    public PlayerAnimationPacketOut(Player player, PlayerAnimation animation) {
        super(Packet.PLAYER_ANIMATION);
        entityId = player.getEntityId();
        this.animation = animation;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeByte(animation.id);
    }
}
