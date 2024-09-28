package domrbeeson.gamma.event.events;

import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class BlockDropItemEvent extends CancellableEvent implements Event.WorldEvent {

    private final int x, y, z;
    private final byte id, metadata;
    private final List<Item> drops;

    public BlockDropItemEvent(Chunk chunk, int x, int y, int z, byte id, byte metadata, List<Item> drops) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
        this.metadata = metadata;
        this.drops = drops;
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

    public byte getId() {
        return id;
    }

    public byte getMetadata() {
        return metadata;
    }

    public List<Item> getDrops() {
        return drops;
    }

}
