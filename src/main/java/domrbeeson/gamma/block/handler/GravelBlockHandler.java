package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GravelBlockHandler extends FallingBlockHandler {

    private static final List<Item> GRAVEL = List.of(Material.GRAVEL.getItem());
    private static final List<Item> FLINT = List.of(Material.FLINT.getItem());

    public GravelBlockHandler() {
        super(Material.GRAVEL);
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        if (ThreadLocalRandom.current().nextInt(10) == 0) {
            return FLINT;
        }
        return GRAVEL;
    }
}
