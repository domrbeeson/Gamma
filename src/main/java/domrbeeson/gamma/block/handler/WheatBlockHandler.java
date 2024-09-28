package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WheatBlockHandler extends BlockHandler {

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        List<Item> drops = new ArrayList<>();
        if (metadata == 7) {
            drops.add(Material.WHEAT.getItem());
            drops.add(Material.WHEAT_SEEDS.getItem(ThreadLocalRandom.current().nextInt(1, 5)));
        } else {
            drops.add(Material.WHEAT_SEEDS.getItem());
        }
        return drops;
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long ticks) {
        if (metadata >= 7) {
            return;
        }
        chunk.setBlock(Block.getChunkRelativeX(x), (byte) y, Block.getChunkRelativeZ(z), id, ++metadata);
    }

}
