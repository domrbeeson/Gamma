package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class EntityEquipmentPacketOut extends PacketOut {

    private final int entityId;
    private final short slot, itemId;

    public EntityEquipmentPacketOut(Entity<?> entity, EquipmentSlot slot, short itemId) {
        super(Packet.ENTITY_EQUIPMENT);
        this.entityId = entity.getEntityId();
        this.slot = (short) slot.ordinal();
        this.itemId = itemId;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeShort(slot);
        stream.writeShort(itemId);
        stream.writeShort(0); // TODO is this damage?
    }

    public enum EquipmentSlot {
        HELD_ITEM,
        HELMET,
        CHEST,
        LEGS,
        BOOTS
    }
}
