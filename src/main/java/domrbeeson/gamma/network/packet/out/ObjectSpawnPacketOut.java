package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.object.ObjectEntity;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class ObjectSpawnPacketOut extends PacketOut {

    private final int entityId;
    private final byte type;
    private final Pos.EncodedPos packedPos;

    public ObjectSpawnPacketOut(ObjectEntity entity) {
        super(Packet.OBJECT_SPAWN);
        this.entityId = entity.getEntityId();
        type = entity.getType().id;
        packedPos = entity.getPos().encode();
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeByte(type);
        stream.writeInt(packedPos.x());
        stream.writeInt(packedPos.y());
        stream.writeInt(packedPos.z());
        stream.writeInt(0); // TODO this is the tracked entity ID that shot an arrow/fireball
    }

}
