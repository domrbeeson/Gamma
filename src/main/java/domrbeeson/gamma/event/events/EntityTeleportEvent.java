package domrbeeson.gamma.event.events;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.world.World;

public class EntityTeleportEvent extends CancellableEvent implements Event.WorldEvent {

    private final Entity<?> entity;
    private final World currentWorld, newWorld;
    private final Pos currentPos, newPos;

    public EntityTeleportEvent(Entity<?> entity, World currentWorld, Pos currentPos, World newWorld, Pos newPos) {
        this.entity = entity;
        this.currentWorld = currentWorld;
        this.currentPos = currentPos;
        this.newWorld = newWorld;
        this.newPos = newPos;
    }

    public Entity<?> getEntity() {
        return entity;
    }

    public World getCurrentWorld() {
        return currentWorld;
    }

    public Pos getCurrentPos() {
        return currentPos;
    }

    public World getNewWorld() {
        return newWorld;
    }

    public Pos getNewPos() {
        return newPos;
    }

}
