package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.inventory.ProgressBarInventory;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class WindowProgressBarPacketOut extends PacketOut {

    private final byte windowId;
    private final ProgressBarInventory inv;
    private final Action action;

    public WindowProgressBarPacketOut(ProgressBarInventory inv, Action action) {
        super(Packet.WINDOW_PROGRES_BAR);
        windowId = inv.getType().id;
        this.inv = inv;
        this.action = action;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeByte(windowId);
        stream.writeShort(action.id);
        stream.writeShort(inv.getProgress(action.maxValue));
        if (action == Action.FURNACE_ARROW) {
            System.out.println("Sending arrow progress " + inv.getProgress(action.maxValue) + " / " + action.maxValue + " [window id " + windowId + "]");
        }
    }

    public enum Action {
        FURNACE_ARROW(0, 180),
        FURNACE_FIRE(1, 250),
        ;

        public final short id, maxValue;

        Action(int id, int maxValue) {
            this.id = (short) id;
            this.maxValue = (short) maxValue;
        }
    }

}
