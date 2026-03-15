package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.event.events.block.BlockChangeEvent;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import org.jetbrains.annotations.Nullable;

public class FireBlockHandler implements BlockHandler {

    @Override
    public void onPlace(MinecraftServer server, BlockChangeEvent event, @Nullable Player player) {
        // TODO detect empty nether portal
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long tick) {
        // TODO spread
    }

}
