package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.world.Chunk;

public class SnowLayerBlockHandler extends BlockHandler {

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean tick(MinecraftServer server, Block block, long ticks) { // TODO need to fix
        Chunk chunk = block.chunk();
        int x = block.x();
        int y = block.y();
        int z = block.z();
        byte blockBelowId = chunk.getBlockId(x, y - 1, z);
        if (!server.getBlockHandlers().get(blockBelowId).isSolid()) {
            chunk.setBlock(x, y, z, (byte) 0);
            return true;
        }
        return false;
    }

}
