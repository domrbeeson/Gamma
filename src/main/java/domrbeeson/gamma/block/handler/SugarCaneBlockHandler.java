package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;

import java.util.List;

public class SugarCaneBlockHandler extends PlantStackBlockHandler {

    private static final int GROW_HEIGHT = 3;
    private static final List<Item> DROPS = List.of(new Item(Material.SUGAR_CANE_ITEM));
    private static final byte[] GROW_ON_BLOCKS = new byte[] {
            Material.SUGAR_CANE_BLOCK.blockId,
            Material.GRASS.blockId,
            Material.SAND.blockId
    };

    public SugarCaneBlockHandler() {
        super(Material.SUGAR_CANE_BLOCK.blockId);
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        return DROPS;
    }

    @Override
    public void onLeftClick(MinecraftServer server, Block block, Player player) {
        breakStack(block);
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long tick) {
        if (chunk.getBlockId(x, y + 1, z) != id && chunk.getBlockId(x, y - GROW_HEIGHT + 1, z) != id) {
            // Sugar cane grows after receiving 16 random ticks
            metadata++;
            if (metadata > 15) {
                chunk.setBlock(x, y + 1, z, id, (byte) 0);
            } else {
                // TODO does sugar cane do a block update when it changes metadata?
                chunk.directlySetBlock(x, y, z, id, metadata);
            }
        } else if (!canPlace(chunk, x, y, z)) {
            breakStack(chunk.getBlock(x, y, z));
        } else if (!bottomBlockHasWater(chunk, x, y - 1, z)) {
            breakStack(chunk.getBlock(x, y, z));
        }
    }

    @Override
    public boolean canPlace(Chunk chunk, int x, int y, int z) {
        byte blockBelowId = chunk.getWorld().getBlockId(x, y - 1, z);
        for (byte id : GROW_ON_BLOCKS) {
            if (id == blockBelowId) {
                return bottomBlockHasWater(chunk, x, y, z);
            }
        }
        return false;
    }

    private boolean bottomBlockHasWater(Chunk chunk, int x, int y, int z) {
        byte id;
        World world = chunk.getWorld();
        y = getGroundY(chunk, x, y, z);

        chunk = world.getLoadedChunk((x + 1) >> 4, z >> 4);
        if (chunk != null) {
            id = chunk.getBlockId(x + 1, y, z);
            if (id == 8 || id == 9) {
                return true;
            }
        }

        chunk = world.getLoadedChunk((x - 1) >> 4, z >> 4);
        if (chunk != null) {
            id = chunk.getBlockId(x - 1, y, z);
            if (id == 8 || id == 9) {
                return true;
            }
        }

        chunk = world.getLoadedChunk(x >> 4, (z + 1) >> 4);
        if (chunk != null) {
            id = chunk.getBlockId(x, y, z + 1);
            if (id == 8 || id == 9) {
                return true;
            }
        }

        chunk = world.getLoadedChunk(x >> 4, (z - 1) >> 4);
        if (chunk != null) {
            id = chunk.getBlockId(x, y, z - 1);
            if (id == 8 || id == 9) {
                return true;
            }
        }

        return false;
    }

}
