package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class EntityTeleportPacketOut extends PacketOut {

    private final int entityId;
    private final Pos.EncodedPos encodedPos;

    public EntityTeleportPacketOut(Entity<?> entity, Pos newPos) {
        super(Packet.ENTITY_TELEPORT);
        this.entityId = entity.getEntityId();
        encodedPos = entity.getPos().difference(newPos).encode();
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeInt(encodedPos.x());
        stream.writeInt(encodedPos.y());
        stream.writeInt(encodedPos.z());
        stream.writeByte(encodedPos.yaw());
        stream.writeByte(encodedPos.pitch());
    }
}
