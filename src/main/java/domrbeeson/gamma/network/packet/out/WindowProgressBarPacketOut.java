package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.inventory.Inventory;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class WindowProgressBarPacketOut extends PacketOut {

    private final byte windowId;
    private final Action action;
    private final short value;

    public WindowProgressBarPacketOut(Inventory inv, Action action, short value) {
        super(Packet.WINDOW_PROGRESS_BAR);
        this.windowId = inv.getType().id;
        this.action = action;
        this.value = value;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeByte(windowId);
        stream.writeShort(action.ordinal());
        stream.writeShort(value);
    }

    public enum Action {
        FURNACE_ARROW,
        FURNACE_FIRE,
        FURNACE_TOTAL_FUEL,
        ;
    }

}
