package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.inventory.CraftingInventory;
import domrbeeson.gamma.player.Player;

public class CraftingTableBlockHandler extends SelfDropBlockHandler {

    @Override
    public boolean onRightClick(MinecraftServer server, Block block, Player player) {
        player.openInventory(new CraftingInventory(server.getRecipeManager()));
        return true;
    }

}
