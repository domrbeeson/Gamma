package domrbeeson.gamma.event.events;

import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.player.Player;

public class PlayerRightClickBlockEvent extends CancellableEvent implements Event.WorldEvent {

    private final Player player;
    private final int x, z;
    private final byte y;
    private final Item heldItem;

    public PlayerRightClickBlockEvent(Player player, int x, byte y, int z, Item heldItem) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
        this.heldItem = heldItem;
    }

    public Player getPlayer() {
        return player;
    }

    public int getX() {
        return x;
    }

    public byte getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Item getHeldItem() {
        return heldItem;
    }

}
