package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.Tickable;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.world.Chunk;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class TileEntity implements Tickable {

    private final Chunk chunk;
    private final int x, y, z;

    public TileEntity(Chunk chunk, int x, int y, int z) {
        this.chunk = chunk;
        this.x = x;
        this.y = y;
        this.z = z;
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

    public static short packLocation(int x, int y, int z) {
        byte relativeX = Block.getChunkRelativeCoord(x);
        byte relativeZ = Block.getChunkRelativeCoord(z);
        return (short) ((relativeX & 15) << 4 | (relativeZ & 15) | y << 8);
    }

    public short packLocation() {
        return packLocation(x, y, z);
    }

    @Nullable
    public static TileEntity getFromPackedLocation(int x, int y, int z, Map<Short, TileEntity> tileEntities) {
        return tileEntities.get(packLocation(x, y, z));
    }

}
