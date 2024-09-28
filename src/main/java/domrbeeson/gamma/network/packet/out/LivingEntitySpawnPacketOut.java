package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.EntityMetadata;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class LivingEntitySpawnPacketOut extends PacketOut {

    private final int entityId;
    private final byte type;
    private final Pos.EncodedPos encodedPos;
    private final EntityMetadata metadata;

    public LivingEntitySpawnPacketOut(LivingEntity<?> entity) {
        super(Packet.LIVING_ENTITY_SPAWN);
        this.entityId = entity.getEntityId();
        this.type = entity.getType().id;
        this.encodedPos = entity.getPos().encode();
        metadata = entity.getMetadata();
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeByte(type);
        stream.writeInt(encodedPos.x());
        stream.writeInt(encodedPos.y());
        stream.writeInt(encodedPos.z());
        stream.writeByte(encodedPos.yaw());
        stream.writeByte(encodedPos.pitch());
        metadata.writeChanges(protocol, stream);
        stream.writeByte(127);
    }

}
