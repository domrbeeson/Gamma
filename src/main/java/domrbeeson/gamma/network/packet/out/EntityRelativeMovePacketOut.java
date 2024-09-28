package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class EntityRelativeMovePacketOut extends PacketOut {

    private final int entityId;
    private final Pos.EncodedPos deltaPos;

    public EntityRelativeMovePacketOut(Entity<?> entity, Pos.EncodedPos deltaPos) {
        super(Packet.ENTITY_RELATIVE_MOVE);
        this.entityId = entity.getEntityId();
        this.deltaPos = deltaPos;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeByte((byte) deltaPos.x());
        stream.writeByte((byte) deltaPos.y());
        stream.writeByte((byte) deltaPos.z());
    }
}
