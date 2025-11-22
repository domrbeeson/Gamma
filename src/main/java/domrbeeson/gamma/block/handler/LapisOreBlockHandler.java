package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.List;
import java.util.SplittableRandom;

public class LapisOreBlockHandler extends ToolsDropBlockHandler {

    private final SplittableRandom random = new SplittableRandom();

    public LapisOreBlockHandler() {
        super(Material.AIR,
                Material.STONE_PICKAXE.id,
                Material.IRON_PICKAXE.id,
                Material.DIAMOND_PICKAXE.id
        );
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        if (canBreakWithTool(toolId)) {
            return List.of(new Item(Material.LAPIS_LAZULI, random.nextInt(4, 9)));
        }
        return List.of();
    }
}
