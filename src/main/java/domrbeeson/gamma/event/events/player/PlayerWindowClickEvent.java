package domrbeeson.gamma.event.events.player;

import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.event.events.CancellableEvent;
import domrbeeson.gamma.inventory.Inventory;
import domrbeeson.gamma.network.MouseButton;
import domrbeeson.gamma.player.Player;

public class PlayerWindowClickEvent extends CancellableEvent implements Event.GlobalEvent {

    private final Player player;
    private final Inventory inventory;
    private final short slot;
    private final MouseButton button;

    public PlayerWindowClickEvent(Player player, Inventory inventory, short slot, MouseButton button) {
        this.player = player;
        this.inventory = inventory;
        this.slot = slot;
        this.button = button;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public short getSlot() {
        return slot;
    }

    public MouseButton getButton() {
        return button;
    }

}
