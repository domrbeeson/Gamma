package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.inventory.Inventory;
import domrbeeson.gamma.inventory.PlayerInventory;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.network.MouseButton;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class WindowClickPacketIn extends WorldPacketIn {

    private final byte windowId;
    private final short slot, actionId;
    private final MouseButton button;
    private final boolean shift;

    public WindowClickPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.WINDOW_CLICK, server, connection, stream);

        windowId = stream.readByte();
        slot = stream.readShort();
        button = MouseButton.values()[stream.readByte()];
        actionId = stream.readShort();
        shift = stream.readBoolean();
        if (stream.readShort() > -1) { // Use item values from player's inventory, don't use client provided values
            stream.readByte(); // Amount
            stream.readShort(); // Metadata
        }
    }

    @Override
    public void handle() {
        Player player = getServer().getPlayerManager().get(getConnection());
        Item cursorItem = player.getCursorItem();
        if (slot < 0) {
            if (cursorItem != null) {
                // Drop item
                player.sendMessage("Drop cursor item here");
            }
            return;
        }

        Inventory inv = player.getOpenInventory();
        // If inventory is NOT a player inventory, need to use player inventory when clicked slot goes above bounds of open inventory
        short mappedSlot;
        if (inv != null) {
            if (slot >= inv.getType().slots) {
                mappedSlot = Inventory.PLAYER_INVENTORY_MAPPINGS[(short) (slot - inv.getType().slots)];
                inv = player.getInventory();
            } else {
                mappedSlot = slot;
            }
        } else {
            inv = player.getInventory();
            mappedSlot = ((PlayerInventory) inv).mapRawSlot(slot);
        }

        inv.click(player, mappedSlot, button);
    }

}
