package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class ClayBlockHandler implements BlockHandler {

    private static final List<Item> DROPS = List.of(new Item(Material.CLAY, 4));

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        return DROPS;
    }
}
