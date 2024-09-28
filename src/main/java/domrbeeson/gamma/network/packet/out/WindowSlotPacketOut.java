package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.inventory.InventoryType;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class WindowSlotPacketOut extends PacketOut {

    private final byte id, amount;
    private final short slot, itemId, metadata;

    public WindowSlotPacketOut(InventoryType type, short slot, Item item) {
        this(type, slot, item.id(), item.metadata(), item.amount());
    }

    public WindowSlotPacketOut(InventoryType type, short slot, short itemId, short metadata, byte amount) {
        super(Packet.PLAYER_INVENTORY_SLOT);
        id = type == InventoryType.PLAYER ? (byte) 0 : (byte) 1;
        this.slot = slot;
        this.itemId = itemId;
        this.metadata = metadata;
        this.amount = amount;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeByte(id);
        stream.writeShort(slot);
        if (itemId > 0) {
            stream.writeShort(itemId);
            stream.writeByte(amount);
            stream.writeShort(metadata);
        } else {
            stream.writeShort(-1);
        }
    }

}
