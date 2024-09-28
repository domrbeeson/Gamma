package domrbeeson.gamma.event.events;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.event.Event;

public class EntitySpawnEvent extends CancellableEvent implements Event.WorldEvent {

    private final Entity<?> entity;
    private final SpawnReason reason;

    public EntitySpawnEvent(Entity<?> entity, SpawnReason reason) {
        this.entity = entity;
        this.reason = reason;
    }

    public Entity<?> getEntity() {
        return entity;
    }

    public SpawnReason getSpawnReason() {
        return reason;
    }

    public enum SpawnReason {
        BLOCK_DROP,
        ;
    }

}
