package domrbeeson.gamma.event.events;

import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.inventory.Inventory;
import domrbeeson.gamma.network.MouseButton;
import domrbeeson.gamma.player.Player;

public class PlayerWindowClickEvent extends CancellableEvent implements Event.GlobalEvent {

    private final Player player;
    private final Inventory inventory;
    private final int slot;
    private final MouseButton button;

    public PlayerWindowClickEvent(Player player, Inventory inventory, int slot, MouseButton button) {
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

    public int getSlot() {
        return slot;
    }

    public MouseButton getButton() {
        return button;
    }

}
