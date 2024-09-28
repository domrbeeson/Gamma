package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.world.Chunk;

public abstract class StackBlockHandler extends BlockHandler {

    protected abstract boolean canGrowOnBlock(short blockId);

    @Override
    public boolean tick(MinecraftServer server, Block block, long tick) {
        if (!canGrowOnBlock(block.chunk().getBlockId(block.x(), block.y() - 1, block.z()))) {
            breakStack(block);
            return true;
        }
        return false;
    }

    protected final void breakStack(Block block) {
        Chunk chunk = block.chunk();
        int x = block.x();
        int y = block.y();
        int z = block.z();
        do {
            chunk.breakBlock(x, y, z);
        } while (chunk.getBlockId(x, ++y, z) == 83);
    }

}
