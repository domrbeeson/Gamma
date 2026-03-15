package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class WheatBlockHandler extends InstantBreakBlockHandler {

    public static final byte FULLY_GROWN_METADATA = 7;

    private final SplittableRandom random = new SplittableRandom();

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        List<Item> drops = new ArrayList<>();
        if (metadata == FULLY_GROWN_METADATA) {
            drops.add(new Item(Material.WHEAT));
            drops.add(new Item(Material.WHEAT_SEEDS, random.nextInt(1, 5)));
        } else {
            drops.add(new Item(Material.WHEAT_SEEDS));
        }
        return drops;
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long ticks) {
        if (chunk.getBlockLight(x, y + 1, z) < 9) {
            return;
        }
        if (metadata >= FULLY_GROWN_METADATA) {
            return;
        }

        float growthRate = 1f;

        World world = chunk.getWorld();
        for (int ix = x - 1; ix < x + 1; ix++) {
            for (int iz = z - 1; iz < z + 1; iz++) {
                float addGrowthRate = 0f;
                Chunk ch = world.getChunk(ix >> 4, iz >> 4);
                if (ch.getBlockId(ix, y - 1, iz) == Material.FARMLAND.blockId) {
                    if (ch.getBlockMetadata(ix, y - 1, iz) > 0) {
                        addGrowthRate = 3f;
                    } else {
                        addGrowthRate = 1f;
                    }
                }

                if (ix != x || iz != z) {
                    addGrowthRate /= 4f;
                }

                growthRate += addGrowthRate;
            }
        }

        if ((world.getBlockId(x, y, z - 1) == id || world.getBlockId(x, y, z + 1) == id)
            && (world.getBlockId(x - 1, y, z) == id || world.getBlockId(x + 1, y, z) == id)
            || (world.getBlockId(x - 1, y, z - 1) == id || world.getBlockId(x + 1, y, z - 1) == id || world.getBlockId(x + 1, y, z + 1) == id || world.getBlockId(x - 1, y, z + 1) == id)
        ) {
            growthRate /= 2f;
        }

        if (random.nextInt(100 / (int) growthRate) == 0) {
            chunk.setBlock(Block.getChunkRelativeCoord(x), (byte) y, Block.getChunkRelativeCoord(z), id, ++metadata);
        }

    }

    @Override
    public boolean onRightClick(MinecraftServer server, Block block, Player player) {
        Item heldItem = player.getInventory().getHeldItem();
        if (heldItem.getMaterial() != Material.BONE_MEAL) {
            return false;
        }

        block.chunk().setBlock(block.x(), block.y(), block.z(), block.id(), FULLY_GROWN_METADATA);
        player.getInventory().setHeldItem(heldItem.addAmount(-1));
        return true;
    }

}
