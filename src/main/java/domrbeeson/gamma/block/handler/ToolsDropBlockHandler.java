package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class ToolsDropBlockHandler extends BlockHandler {

    private final List<Item> drop;
    private final short[] toolIds;

    public ToolsDropBlockHandler(Material drop, short... toolIds) {
        this.drop = List.of(drop.getItem());
        this.toolIds = toolIds;
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        if (canBreakWithTool(toolId)) {
            return drop;
        }
        return EMPTY_DROPS;
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
