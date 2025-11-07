package domrbeeson.gamma.block;

import domrbeeson.gamma.block.handler.BlockHandler;
import domrbeeson.gamma.item.Material;
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

    public Material material() {
        return Material.get(id, metadata);
    }

    public BlockHandler getHandler() {
        return BlockHandlers.getBlockHandler(id);
    }

    public static byte getChunkRelativeCoord(int xOrZ) {
        return (byte)(xOrZ - ((xOrZ >> 4) * Chunk.WIDTH));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Block block)) {
            return false;
        }

        return chunk.equals(block.chunk)
                && x == block.x
                && y == block.y
                && z == block.z
                && id == block.id
                && metadata == block.metadata
                ;
    }

}
