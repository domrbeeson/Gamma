package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.inventory.Inventory;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.player.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class WindowClosePacketOut extends PacketOut {

    private final byte inventoryId;

    public WindowClosePacketOut(Player player) {
        super(Packet.CLOSE_WINDOW);
        Inventory openInv = player.getOpenInventory();
        inventoryId = openInv != null ? openInv.getType().id : 0;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeByte(inventoryId);
    }

}
