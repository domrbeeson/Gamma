package domrbeeson.gamma.event.events;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.World;

public class PlayerSpawnEvent extends PlayerWorldEvent {

    private World world;
    private Pos pos;

    public PlayerSpawnEvent(Player player, World world, Pos pos) {
        super(player);
        this.world = world;
        this.pos = pos;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

}
