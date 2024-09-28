package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;

import java.util.List;

public class SugarCaneBlockHandler extends StackBlockHandler {

    private static final int GROW_HEIGHT = 3;
    private static final List<Item> DROPS = List.of(Material.SUGAR_CANE.getItem());
    private static final byte[] GROW_ON_BLOCKS = new byte[] {
            Material.SUGAR_CANE_BLOCK.blockId,
            Material.GRASS.blockId,
            Material.SAND.blockId
    };

    @Override
    public boolean isSolid() {
        return false;
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
        byte relativeX = Block.getChunkRelativeX(x);
        byte relativeZ = Block.getChunkRelativeZ(z);

        byte bottomBlockId;
        if (chunk.getBlockId(relativeX, y + 1, relativeZ) != id && chunk.getBlockId(relativeX, y - GROW_HEIGHT + 1, relativeZ) != id) {
            // Sugar cane grows after receiving 16 random ticks
            metadata++;
            if (metadata > 15) {
                chunk.setBlock(x, y + 1, z, id, (byte) 0);
            } else {
                chunk.directlySetBlock(relativeX, y, relativeZ, id, metadata);
            }
        } else if (!canGrowOnBlock((bottomBlockId = chunk.getBlockId(relativeX, y - 1, relativeZ))) || (bottomBlockId != id && !bottomBlockHasWater(chunk.getWorld(), x, y - 1, z))) {
            breakStack(chunk.getBlock(x, y, z));
        }
    }

    @Override
    protected boolean canGrowOnBlock(short blockId) {
        for (byte id : GROW_ON_BLOCKS) {
            if (id == blockId) {
                return true;
            }
        }
        return false;
    }

    private boolean bottomBlockHasWater(World world, int x, int y, int z) {
        byte id;

        Chunk chunk = world.getLoadedChunk((x + 1) >> 4, z >> 4);
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
