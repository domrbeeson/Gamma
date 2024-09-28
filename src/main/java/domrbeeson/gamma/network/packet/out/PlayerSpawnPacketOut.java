package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.player.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerSpawnPacketOut extends PacketOut {

    private final int entityId;
    private final String username;
    private final Pos.EncodedPos encodedPos;
    private final short heldItemId;

    public PlayerSpawnPacketOut(Player player) {
        super(Packet.PLAYER_SPAWN);
        entityId = player.getEntityId();
        username = player.getUsername();
        encodedPos = player.getPos().encode();
        heldItemId = 0;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        writeString(protocol, stream, username);
        stream.writeInt(encodedPos.x());
        stream.writeInt(encodedPos.y());
        stream.writeInt(encodedPos.z());
        stream.writeByte(encodedPos.yaw());
        stream.writeByte(encodedPos.pitch());
        stream.writeShort(heldItemId);
    }

}
