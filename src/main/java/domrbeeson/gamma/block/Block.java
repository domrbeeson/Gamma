package domrbeeson.gamma.block;

import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;

public record Block(
        World world,
        Chunk chunk,
        int x,
        int y,
        int z,
        byte id,
        byte metadata,
        byte skyLight,
        byte blockLight
) {

    public static byte getChunkRelativeX(int x) {
        return (byte)(x - ((x >> 4) * Chunk.WIDTH));
    }

    public static byte getChunkRelativeZ(int z) {
        return (byte)(z - ((z >> 4) * Chunk.WIDTH));
    }

}
