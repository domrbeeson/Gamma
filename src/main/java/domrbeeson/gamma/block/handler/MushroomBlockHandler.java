package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.world.Chunk;

public class MushroomBlockHandler extends SelfDropBlockHandler {

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long ticks) {
        // TODO spread
    }

}
