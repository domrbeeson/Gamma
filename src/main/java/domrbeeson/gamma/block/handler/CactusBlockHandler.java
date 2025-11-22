package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.BlockHandlers;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class CactusBlockHandler extends PlantStackBlockHandler {

    private static final int GROW_HEIGHT = 3;
    private static final List<Item> DROPS = List.of(new Item(Material.CACTUS));

    public CactusBlockHandler() {
        super(Material.CACTUS.blockId);
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        return DROPS;
    }

    @Override
    public boolean canPlace(Chunk chunk, int x, int y, int z) {
        byte blockBelowId = chunk.getBlockId(x, y - 1, z);
        if (blockBelowId != Material.SAND.blockId && blockBelowId != Material.CACTUS.blockId) {
            return false;
        }

        boolean keepCactus = true;
        if (shouldBreak(chunk, x + 1, y, z)) {
            keepCactus = false;
        } else if (shouldBreak(chunk, x, y, z + 1)) {
            keepCactus = false;
        } else if (shouldBreak(chunk, x - 1, y, z)) {
            keepCactus = false;
        } else if (shouldBreak(chunk, x, y, z - 1)) {
            keepCactus = false;
        }
        return keepCactus;
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long tick) {
        BlockHandler above = BlockHandlers.getBlockHandler(chunk.getBlockId(x, y + 1, z));
        if (above.isSolid()) {
            return;
        }

        int groundY = getGroundY(chunk, x, y, z);
        if (y - groundY >= GROW_HEIGHT) {
            return;
        }

        chunk.setBlock(x, y + 1, z, Material.CACTUS);
    }

    private boolean shouldBreak(Chunk chunk, int x, int y, int z) {
        return chunk.getBlockId(x, y, z) != Material.AIR.blockId;
    }

}
