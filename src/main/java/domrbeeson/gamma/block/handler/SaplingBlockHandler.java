package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class SaplingBlockHandler implements BlockHandler {

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte blockId, byte blockMaterial, short toolId) {
        return List.of(new Item(blockId, blockMaterial));
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long tick) {
        // TODO grow tree
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
