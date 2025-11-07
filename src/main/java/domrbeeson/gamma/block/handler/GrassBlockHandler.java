package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.List;
import java.util.SplittableRandom;

public class GrassBlockHandler extends FarmlandBlockHandler {

    private static final List<Item> DROPS = List.of(Material.DIRT.getItem());

    private final SplittableRandom random = new SplittableRandom(); // TODO this won't work in a multithreaded environment, so need to pass the random through to randomTick from the chunk shard

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
            return DROPS;
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long tick) {
        Block blockAbove = chunk.getBlock(x, y + 1, z);
        if (blockAbove == null) {
            return;
        }
        if (blockAbove.blockLight() < 4 && blockAbove.material().blockOpacity > 2) {
            if (random.nextInt(4) != 0) {
                return;
            }
            chunk.setBlock(x, y, z, Material.DIRT); // TODO disabled until lighting works
        } else if (blockAbove.blockLight() >= 9) {
            x += random.nextInt(3) - 1;
            y += random.nextInt(5) - 3;
            z += random.nextInt(3) - 1;

            if (chunk.getBlockId(x, y, z) == Material.DIRT.blockId) {
                blockAbove = chunk.getBlock(x, y + 1, z);
                if (blockAbove != null && blockAbove.blockLight() >= 4 && blockAbove.material().blockOpacity <= 2) {
                    chunk.setBlock(x, y, z, Material.GRASS); // TODO disabled until lighting works
                }
            }
        }
    }

}
