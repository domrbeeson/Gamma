package domrbeeson.gamma.event.events;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.event.Event;

public class EntityMoveEvent extends CancellableEvent implements Event.WorldEvent {

    private final Entity<?> entity;
    private final Pos currentPos, newPos;

    public EntityMoveEvent(Entity<?> entity, Pos currentPos, Pos newPos) {
        this.entity = entity;
        this.currentPos = currentPos;
        this.newPos = newPos;
    }

    public Entity<?> getEntity() {
        return entity;
    }

    public Pos getCurrentPos() {
        return currentPos;
    }

    public Pos getNewPos() {
        return newPos;
    }

}
