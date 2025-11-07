package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.BlockHandlers;
import domrbeeson.gamma.item.Material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        // TODO support turning off redstone signal
        server.broadcast("updating redstone torch");

        Set<Block> checkedBlocks = new HashSet<>();
        List<Block> blocksToUpdate = new ArrayList<>() {{
            add(block.chunk().getBlock(block.x() + 1, block.y(), block.z()));
            add(block.chunk().getBlock(block.x() - 1, block.y(), block.z()));
            add(block.chunk().getBlock(block.x(), block.y() - 1, block.z()));
            add(block.chunk().getBlock(block.x(), block.y(), block.z() + 1));
            add(block.chunk().getBlock(block.x(), block.y(), block.z() - 1));
        }};

        byte power = 15;
        while (!blocksToUpdate.isEmpty()) {
            List<Block> newBlocksToUpdate = new ArrayList<>();
            for (Block b : blocksToUpdate) {
                if (BlockHandlers.getBlockHandler(b.id()).canPower()) {
                    if (!checkedBlocks.contains(b)) {
                        newBlocksToUpdate.add(b);
                    }
                }
                if (b.id() == Material.REDSTONE_WIRE.blockId) {
                    server.broadcast("powering redstone wire");
                    b.chunk().setBlock(b.x(), b.y(), b.z(), b.id(), (byte) 15);
                } else {
                    // TODO schedule a block update for block being powered
                }
            }
            power--;
            checkedBlocks.addAll(blocksToUpdate);
            blocksToUpdate.clear();
            blocksToUpdate.addAll(newBlocksToUpdate);
        }

        return true;
    }

}
