package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;

public class FireBlockHandler extends BlockHandler {

    @Override
    public void onPlace(MinecraftServer server, Block block, Player player) {
        // TODO detect empty nether portal
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long tick) {
        // TODO spread
    }

}
