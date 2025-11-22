package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class ToolsDropBlockHandler implements BlockHandler {

    private final List<Item> drops;
    private final short[] toolIds;

    public ToolsDropBlockHandler(Material drop, short... toolIds) {
        this.drops = List.of(new Item(drop));
        this.toolIds = toolIds;
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        if (canBreakWithTool(toolId)) {
            return drops;
        }
        return List.of();
    }

    protected boolean canBreakWithTool(short toolId) {
        for (short id : toolIds) {
            if (id == toolId) {
                return true;
            }
        }
        return false;
    }

}
