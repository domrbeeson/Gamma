package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.inventory.Inventory;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class WindowItemsPacketOut extends PacketOut {

    private final byte windowId;
    private final short amountOfItems;
    private final Item[] items;
    private final byte slots;

    // TODO this doesn't work
    public WindowItemsPacketOut(Inventory inventory) {
        super(Packet.WINDOW_ITEMS);
        windowId = inventory.getType().id;
        amountOfItems = inventory.getSlotsPopulated();
        items = inventory.getSlots();
        this.slots = inventory.getType().slots;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeByte(windowId);
        stream.writeShort(slots);
        short id;
        for (int i = 0; i < slots; i++) {
            id = items[i] == null ? 0 : items[i].id();
            if (id <= 0) {
                stream.writeShort(-1);
                continue;
            }
            stream.writeShort(id);
            stream.writeByte(items[i].amount());
            stream.writeShort(items[i].metadata());
        }
    }

}
