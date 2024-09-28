package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.player.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerUseBedPacketOut extends PacketOut {

    private final int entityId;
    private final int x, y, z;

    public PlayerUseBedPacketOut(Player player, Block block) {
        super(Packet.PLAYER_USE_BED);
        this.entityId = player.getEntityId();
        x = block.x();
        y = block.y();
        z = block.z();
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeByte(0);
        stream.writeInt(x);
        stream.writeByte(y);
        stream.writeInt(z);
    }
}
