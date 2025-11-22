package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.List;
import java.util.Set;

public class RedstoneBlockHandler extends InstantBreakBlockHandler {

    private static final List<Item> DROPS = List.of(
        new Item(Material.REDSTONE)
    );

    private static final Set<Material> PLACE_DENYLIST = Set.of(
        Material.REDSTONE_WIRE
    );

    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte blockId, byte blockMetadata, short toolId) {
        return DROPS;
    }

    @Override
    public boolean canPlace(Chunk chunk, int x, int y, int z) {
        return !PLACE_DENYLIST.contains(chunk.getMaterial(x, y - 1, z));
    }

    @Override
    public boolean canPower() {
        return true;
    }

}
