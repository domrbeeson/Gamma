package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.BlockHandlers;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;

import java.util.List;
import java.util.SplittableRandom;

public class FarmlandBlockHandler implements BlockHandler {

    private static final List<Item> DROPS = List.of(new Item(Material.DIRT));

    private final SplittableRandom random = new SplittableRandom();

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte blockId, byte blockMetadata, short toolId) {
        return DROPS;
    }

    @Override
    public boolean onRightClick(MinecraftServer server, Block block, Player player) {
        Item heldItem = player.getInventory().getHeldItem();
        if (heldItem.getMaterial().isHoe()) {
            player.damageTool();

            block.chunk().setBlock(block.x(), block.y(), block.z(), Material.FARMLAND);
            return true;
        } else if (heldItem.getMaterial() == Material.WHEAT_SEEDS) {
            BlockHandler aboveBlock = BlockHandlers.getBlockHandler(block.chunk().getBlockId(block.x(), block.y() + 1, block.z()));
            if (!aboveBlock.isSolid()) {
                block.world().setBlock(block.x(), block.y() + 1, block.z(), Material.WHEAT_CROPS);
                return true;
            }
        }

        return false;
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long tick) {
        if (random.nextInt(5) == 0) {
            if (!isWaterNearby(chunk, x, y, z)) { // TODO don't continue if lightning can strike at x, y + 1, z
                if (metadata > 0) {
                    chunk.setBlock(x, y, z, Material.FARMLAND.blockId, (byte) (metadata - 1));
                } else if (metadata == 0 && chunk.getBlockId(x, y + 1, z) != Material.WHEAT_CROPS.blockId) {
                    chunk.setBlock(x, y, z, Material.DIRT);
                }
            } else {
                chunk.setBlock(x, y, z, Material.FARMLAND.blockId, (byte) 7);
            }
        }
    }

    private boolean isWaterNearby(Chunk chunk, int centreX, int centreY, int centreZ) {
        // TODO this causes excessive memory allocation for some reason
        World world = chunk.getWorld();
        for (int x = centreX - 4; x <= centreX + 4; x++) {
            for (int z = centreZ - 4; z <= centreZ + 4; z++) {
                if (chunk == null || !chunk.isInChunk(x, z)) {
                    chunk = world.getLoadedChunk(x >> 4, z >> 4);
                }

                if (chunk != null) {
                    for (int y = centreY; y < centreY + 4; y++) {
                        byte blockId = chunk.getBlockId(x, y, z);
                        if (blockId == Material.WATER_SOURCE.blockId && blockId == Material.WATER_FLOWING.blockId) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // TODO trample and return to dirt

}
