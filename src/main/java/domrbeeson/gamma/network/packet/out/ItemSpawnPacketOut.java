package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.entity.ItemEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class ItemSpawnPacketOut extends PacketOut {

    private final int entityId;
    private final short itemId, metadata;
    private final byte amount, yaw, pitch, roll;
    private final Pos.EncodedPos encodedPos;

    public ItemSpawnPacketOut(ItemEntity item) {
        super(Packet.ITEM_SPAWN);
        this.entityId = item.getEntityId();
        this.itemId = item.getItem().id();
        this.metadata = item.getItem().metadata();
        this.amount = item.getItem().amount();
        this.encodedPos = item.getPos().encode();

        // TODO
        this.yaw = 0;
        this.pitch = 0;
        this.roll = 0;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeShort(itemId);
        stream.writeByte(amount);
        stream.writeShort(metadata);
        stream.writeInt(encodedPos.x());
        stream.writeInt(encodedPos.y());
        stream.writeInt(encodedPos.z());
        stream.writeByte(yaw);
        stream.writeByte(pitch);
        stream.writeByte(roll);
    }
}
