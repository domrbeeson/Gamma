package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.world.Chunk;

public class SpawnerBlockHandler extends BlockHandler {

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long ticks) {
        // TODO spawn entities, get spawner type from tile entity data
    }

}
