package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;

public abstract class RedstoneSourceBlockHandler implements BlockHandler {

    private boolean powered;

    public RedstoneSourceBlockHandler(boolean poweredByDefault) {
        this.powered = poweredByDefault;
    }

    protected void setPowered(Block block, boolean powered) {
        this.powered = powered;
        block.chunk().scheduleBlockUpdate(block.x(), block.y(), block.z());
    }

    public boolean update(MinecraftServer server, Block block, long tick) {
        byte powerLevel = powered ? (byte) 15 : (byte) 0;

        // If the next redstone wire power level is less than the previous one, set to 0
        // Else if the next redstone wire power level >= the previous one, go backwards down the wire

        return false;
    }

    private void updateNextBlock(int x, int z) {

    }

}
