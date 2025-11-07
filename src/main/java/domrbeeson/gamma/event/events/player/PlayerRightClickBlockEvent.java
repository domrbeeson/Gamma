package domrbeeson.gamma.event.events.player;

import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.event.events.CancellableEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Direction;

public class PlayerRightClickBlockEvent extends CancellableEvent implements Event.WorldEvent {

    private final Player player;
    private final int x, y, z;
    private final Direction direction;
    private final Item heldItem;

    // TODO store world + chunk
    public PlayerRightClickBlockEvent(Player player, int x, int y, int z, Direction direction, Item heldItem) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
        this.heldItem = heldItem;
    }

    public Player getPlayer() {
        return player;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Direction getDirection() {
        return direction;
    }

    public Item getHeldItem() {
        return heldItem;
    }

}
