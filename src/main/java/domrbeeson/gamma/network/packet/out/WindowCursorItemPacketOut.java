package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class WindowCursorItemPacketOut extends PacketOut {

    private final short itemId, metadata;
    private final byte amount;

    public WindowCursorItemPacketOut(Item item) {
        this(item.id(), item.metadata(), item.amount());
    }

    public WindowCursorItemPacketOut(short itemId, short metadata, byte amount) {
        super(Packet.PLAYER_INVENTORY_SLOT);
        this.itemId = itemId;
        this.metadata = metadata;
        this.amount = amount;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeByte(-1);
        stream.writeShort(-1);
        if (itemId > 0) {
            stream.writeShort(itemId);
            stream.writeByte(amount);
            stream.writeShort(metadata);
        } else {
            stream.writeShort(-1);
        }
    }

}
