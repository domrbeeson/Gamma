package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class EntityVelocityPacketOut extends PacketOut {

    private final int entityId;
    private final short velX, velY, velZ;

    public EntityVelocityPacketOut(int entityId, short velX, short velY, short velZ) {
        super(Packet.ENTITY_VELOCITY);
        this.entityId = entityId;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeShort(velX);
        stream.writeShort(velY);
        stream.writeShort(velZ);
    }

}
