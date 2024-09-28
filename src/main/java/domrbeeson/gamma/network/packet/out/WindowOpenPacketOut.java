package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.inventory.Inventory;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class WindowOpenPacketOut extends PacketOut {

    private final byte invType, slots;
    private final String name;

    public WindowOpenPacketOut(Inventory inv) {
        super(Packet.OPEN_WINDOW);
        invType = inv.getType().id;
        name = inv.getTitle();
        slots = inv.getType().slots;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeByte(1);
        stream.writeByte(invType);
        stream.writeUTF(name);
        stream.writeByte(slots);
    }
}
