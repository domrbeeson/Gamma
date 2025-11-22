package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class SelfDropBlockHandler implements BlockHandler {

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte blockId, byte blockMetadata, short toolId) {
        return List.of(new Item(blockId, blockMetadata));
    }

}
