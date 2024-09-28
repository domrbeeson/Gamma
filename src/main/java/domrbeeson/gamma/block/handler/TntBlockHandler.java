package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class TntBlockHandler extends PoweredBlockHandler {

    private static final List<Item> FLINT_AND_STEEL_DROPS = List.of(Material.TNT.getItem());

//    private final boolean flintAndSteel = MinecraftServer.getServerSettings().getMinecraftVersion().features.tntRequiresFlintAndSteel();
    private final boolean flintAndSteel = false; // TODO

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        if (flintAndSteel) {
            return FLINT_AND_STEEL_DROPS;
        }
        return EMPTY_DROPS;
    }

    @Override
    public boolean onRightClick(MinecraftServer server, Block me, Player player) {
        if (!flintAndSteel) {
            return false;
        }
        // TODO check if player is holding a flint and steel, then spawn TNT entity
        return true;
    }

    @Override
    public void onPower(MinecraftServer server, Block me, byte powerLevel) {
        // TODO light tnt
    }

//    @Override
//    public void updateAdjacent(Block me, Block adjacent) {
//        // TODO check if redstone was applied
//    }

}
