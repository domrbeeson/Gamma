package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockHandler {

    protected static final List<Item> EMPTY_DROPS = new ArrayList<>();

    public void onBreak(MinecraftServer server, Block block, Player player) {
        block.chunk().breakBlockAsPlayer(player, block.x(), block.y(), block.z());
    }

    public void onPlace(MinecraftServer server, Block block, Player player) {} // TODO not sure this should exist

    public final List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte blockId, byte blockMetadata) {
        return getDrops(server, chunk, x, y, z, blockId, blockMetadata, (short) 0);
    }
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte blockId, byte blockMetadata, short toolId) {
        return EMPTY_DROPS;
    }
    public void onLeftClick(MinecraftServer server, Block block, Player player) {}
    public boolean onRightClick(MinecraftServer server, Block block, Player player) {
        return false;
    }
    public boolean tick(MinecraftServer server, Block block, long tick) {
        return false;
    }
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long tick) {}

    public boolean isSolid() {
        return true;
    }

    public static void updateAdjacentBlocks(Block middle, int ticksInFuture) {
        World world = middle.world();
        int x = middle.x();
        int y = middle.y();
        int z = middle.z();

        Chunk chunk = world.getLoadedChunk((x + 1) >> 4, z >> 4);
        if (chunk != null) {
            chunk.scheduleBlockTick(Chunk.packChunkBlockCoords(x + 1, y, z), ticksInFuture);
        }

        chunk = world.getLoadedChunk((x - 1) >> 4, z >> 4);
        if (chunk != null) {
            chunk.scheduleBlockTick(Chunk.packChunkBlockCoords(x - 1, y, z), ticksInFuture);
        }

        chunk = world.getLoadedChunk(x >> 4, z >> 4);
        if (chunk != null) {
            chunk.scheduleBlockTick(Chunk.packChunkBlockCoords(x, y + 1, z), ticksInFuture);
            chunk.scheduleBlockTick(Chunk.packChunkBlockCoords(x, y - 1, z), ticksInFuture);
        }

        chunk = world.getLoadedChunk(x >> 4, (z + 1) >> 4);
        if (chunk != null) {
            chunk.scheduleBlockTick(Chunk.packChunkBlockCoords(x, y, z + 1), ticksInFuture);
        }

        chunk = world.getLoadedChunk(x >> 4, (z - 1) >> 4);
        if (chunk != null) {
            chunk.scheduleBlockTick(Chunk.packChunkBlockCoords(x, y, z - 1), ticksInFuture);
        }
    }

}
