package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.player.Player;

public class RedstoneTorchBlockHandler extends RedstoneSourceBlockHandler {

    public RedstoneTorchBlockHandler() {
        super(true);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void onLeftClick(MinecraftServer server, Block block, Player player) {
        block.chunk().breakBlockAsPlayer(player, block.x(), block.y(), block.z());
    }

}
