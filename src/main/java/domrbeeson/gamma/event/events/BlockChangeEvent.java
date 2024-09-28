package domrbeeson.gamma.event.events;

import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.world.Chunk;

public class BlockChangeEvent extends CancellableEvent implements Event.WorldEvent {

    private final Chunk chunk;
    private final int x, y, z;
    private final byte currentId, currentMetadata, newId, newMetadata;
    private final boolean tick;

    public BlockChangeEvent(Chunk chunk, int x, int y, int z, byte currentId, byte currentMetadata, byte newId, byte newMetadata, boolean tick) {
        this.chunk = chunk;
        this.x = x;
        this.y = y;
        this.z = z;
        this.currentId = currentId;
        this.currentMetadata = currentMetadata;
        this.newId = newId;
        this.newMetadata = newMetadata;
        this.tick = tick;
    }

    public Chunk getChunk() {
        return chunk;
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

    public byte getCurrentId() {
        return currentId;
    }

    public byte getCurrentMetadata() {
        return currentMetadata;
    }

    public byte getNewId() {
        return newId;
    }

    public byte getNewMetadata() {
        return newMetadata;
    }

    public boolean tick() {
        return tick;
    }

}
