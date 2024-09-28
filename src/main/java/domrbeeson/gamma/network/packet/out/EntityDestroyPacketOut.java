package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class EntityDestroyPacketOut extends PacketOut {

    private final int entityId;

    public EntityDestroyPacketOut(int entityId) {
        super(Packet.ENTITY_DESTROY);
        this.entityId = entityId;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
    }

}
