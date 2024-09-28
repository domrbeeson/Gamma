package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.metadata.EntityMetadata;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class EntityMetadataPacketOut extends PacketOut {

    private final int entityId;
    private final EntityMetadata metadata;

    public EntityMetadataPacketOut(Entity<?> entity) {
        super(Packet.ENTITY_METADATA);
        entityId = entity.getEntityId();
        metadata = entity.getMetadata();
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        metadata.writeChanges(protocol, stream);
        stream.writeByte(127); // Terminate
    }

}
