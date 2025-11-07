package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.BlockHandlers;
import domrbeeson.gamma.item.Material;

public class SnowLayerBlockHandler implements BlockHandler {

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean update(MinecraftServer server, Block block, long ticks) {
        int x = block.x();
        int y = block.y();
        int z = block.z();
        byte blockBelowId = block.chunk().getBlockId(x, y - 1, z);
        if (!BlockHandlers.getBlockHandler(blockBelowId).isSolid()) {
            block.chunk().setBlock(x, y, z, Material.AIR);
            return true;
        }
        return false;
    }

}
