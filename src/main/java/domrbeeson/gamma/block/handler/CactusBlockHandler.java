package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class CactusBlockHandler extends StackBlockHandler {

    private static final int GROW_HEIGHT = 3; // TODO
    private static final List<Item> DROPS = List.of(Material.CACTUS.getItem());

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        return DROPS;
    }

    @Override
    public void onBreak(MinecraftServer server, Block block, Player player) {
        breakStack(block);
    }

    @Override
    protected boolean canGrowOnBlock(short blockId) {
        return blockId == Material.SAND.id;
    }

}
