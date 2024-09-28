package domrbeeson.gamma.event.events;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.world.Chunk;
import org.jetbrains.annotations.Nullable;

public record EntityMoveChunkEvent(Entity<?> entity, @Nullable Chunk oldChunk, @Nullable Chunk newChunk) implements Event.WorldEvent {

}
