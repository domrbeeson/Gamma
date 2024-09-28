package domrbeeson.gamma.event.events;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.World;

public class PlayerTeleportEvent extends CancellableEvent implements Event.GlobalEvent {

    private final Player player;
    private final World world;
    private final Pos pos;

    public PlayerTeleportEvent(Player player, World world, Pos pos) {
        this.player = player;
        this.world = world;
        this.pos = pos;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public Pos getPos() {
        return pos;
    }

}
