package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.BlockHandlers;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.Dimension;
import domrbeeson.gamma.world.Direction;
import domrbeeson.gamma.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class FluidBlockHandler implements BlockHandler {

    private static final int MAX_FLOW_DISTANCE = 8;

    private final byte sourceBlockId;
    private final byte flowingBlockId;
    private final Map<Dimension, Long> updateFrequencyTicks;
    private final Map<Dimension, Byte> dropoff;

    public FluidBlockHandler(byte sourceBlockId, byte flowingBlockId, long updateFrequencyTicks, byte dropoff) {
        this.sourceBlockId = sourceBlockId;
        this.flowingBlockId = flowingBlockId;
        this.updateFrequencyTicks = new HashMap<>();
        for (Dimension dimension : Dimension.values()) {
            this.updateFrequencyTicks.put(dimension, updateFrequencyTicks);
        }
        this.dropoff = new HashMap<>();
        for (Dimension dimension : Dimension.values()) {
            this.dropoff.put(dimension, dropoff);
        }
    }

    public FluidBlockHandler(byte sourceBlockId, byte flowingBlockId, Map<Dimension, Long> updateFrequencyTicks, Map<Dimension, Byte> dropoff) {
        this.sourceBlockId = sourceBlockId;
        this.flowingBlockId = flowingBlockId;
        this.updateFrequencyTicks = updateFrequencyTicks;
        for (Dimension dimension : Dimension.values()) {
            this.updateFrequencyTicks.putIfAbsent(dimension, 5L);
        }
        this.dropoff = dropoff;
        for (Dimension dimension : Dimension.values()) {
            this.dropoff.putIfAbsent(dimension, (byte) 1);
        }
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isLiquid() {
        return true;
    }

    @Override
    public boolean isPermeable() {
        return true;
    }

    @Override
    public boolean update(MinecraftServer server, Block block, long ticks) {
        int x = block.x();
        int y = block.y();
        int z = block.z();
        if (!shouldUpdateThisTick(ticks, block.world().getFormat().getDimension())) {
            block.chunk().scheduleBlockUpdate(x, y, z);
            return false;
        }

        World world = block.world();
        byte height = (byte) (block.metadata() + dropoff.get(world.getFormat().getDimension()));

        if (shouldDrain(block.world(), block.id(), block.metadata(), x, y, z)) {
            if (height >= MAX_FLOW_DISTANCE) {
                block.chunk().setBlock(x, y, z, Material.AIR);
            } else {
                block.chunk().setBlock(x, y, z, block.id(), height);
            }
            return true;
        }

        if (height < MAX_FLOW_DISTANCE && (block.id() == Material.WATER_SOURCE.blockId || block.id() == Material.LAVA_SOURCE.blockId)) {
            flowToBlock(ticks, world, x + 1, y, z, height);
            flowToBlock(ticks, world, x - 1, y, z, height);
            flowToBlock(ticks, world, x, y, z + 1, height);
            flowToBlock(ticks, world, x, y, z - 1, height);
        }

        byte belowBlockId = block.chunk().getBlockId(x, y - 1, z);
        BlockHandler belowBlockHandler = BlockHandlers.getBlockHandler(belowBlockId);
        if (belowBlockHandler.isPermeable()) {
            flowToBlock(ticks, world, x, y - 1, z, (byte) 0);
            return true;
        }

        if (height < MAX_FLOW_DISTANCE) {
            flowToBlock(ticks, world, x + 1, y, z, height);
            flowToBlock(ticks, world, x - 1, y, z, height);
            flowToBlock(ticks, world, x, y, z + 1, height);
            flowToBlock(ticks, world, x, y, z - 1, height);
        }

        return true;
    }

    private boolean shouldDrain(World world, byte id, byte meta, int x, int y, int z) {
        if (id == sourceBlockId) {
            return false;
        }

        byte checkId;

        checkId = world.getChunk(x >> 4, z >> 4).getBlockId(x, y + 1, z);
        if (checkId == sourceBlockId || checkId == flowingBlockId) {
            return false;
        }

        Chunk chunk;
        byte checkMeta;

        chunk = world.getChunk((x + 1) >> 4, z >> 4);
        checkId = chunk.getBlockId(x + 1, y, z);
        checkMeta = chunk.getBlockMetadata(x + 1, y, z);
        if ((checkId == sourceBlockId || checkId == flowingBlockId) && meta > checkMeta) {
            return false;
        }

        chunk = world.getChunk((x - 1) >> 4, z >> 4);
        checkId = chunk.getBlockId(x - 1, y, z);
        checkMeta = chunk.getBlockMetadata(x - 1, y, z);
        if ((checkId == sourceBlockId || checkId == flowingBlockId) && meta > checkMeta) {
            return false;
        }

        chunk = world.getChunk(x >> 4, (z + 1) >> 4);
        checkId = chunk.getBlockId(x, y, z + 1);
        checkMeta = chunk.getBlockMetadata(x, y, z + 1);
        if ((checkId == sourceBlockId || checkId == flowingBlockId) && meta > checkMeta) {
            return false;
        }

        chunk = world.getChunk(x >> 4, (z - 1) >> 4);
        checkId = chunk.getBlockId(x, y, z - 1);
        checkMeta = chunk.getBlockMetadata(x, y, z - 1);
        if ((checkId == sourceBlockId || checkId == flowingBlockId) && meta > checkMeta) {
            return false;
        }

        return true;
    }

    @Nullable
    private Direction getHoleDirection(World world, int x, int y, int z, byte height) {
        int checkDistance = (MAX_FLOW_DISTANCE - height) / dropoff.get(world.getFormat().getDimension());
//        System.out.println("checkDistance: " + checkDistance);
        if (checkDistance == 0) {
            return null;
        }

        for (int i = 1; i < checkDistance; i++) {
            Block block = world.getBlock(x + i, y, z);
            if (BlockHandlers.getBlockHandler(block.id()).isPermeable()) {
                block = world.getBlock(x + i, y - 1, z);
                if (BlockHandlers.getBlockHandler(block.id()).isPermeable()) {
                    return Direction.WEST;
                }
            }

            block = world.getBlock(x - i, y, z);
            if (BlockHandlers.getBlockHandler(block.id()).isPermeable()) {
                block = world.getBlock(x - i, y - 1, z);
                if (BlockHandlers.getBlockHandler(block.id()).isPermeable()) {
                    return Direction.EAST;
                }
            }

            block = world.getBlock(x, y, z + i);
            if (BlockHandlers.getBlockHandler(block.id()).isPermeable()) {
                block = world.getBlock(x, y - 1, z + i);
                if (BlockHandlers.getBlockHandler(block.id()).isPermeable()) {
                    return Direction.NORTH;
                }
            }

            block = world.getBlock(x, y, z - i);
            if (BlockHandlers.getBlockHandler(block.id()).isPermeable()) {
                block = world.getBlock(x, y - 1, z - i);
                if (BlockHandlers.getBlockHandler(block.id()).isPermeable()) {
                    return Direction.SOUTH;
                }
            }
        }

        return Direction.NONE;
    }

    private void flowToBlock(long ticks, World world, int x, int y, int z, byte newHeight) {
        // Fluids do not load new chunks
        Chunk chunk = world.getLoadedChunk(x >> 4, z >> 4);
        if (chunk == null) {
            return;
        }
        byte blockId = chunk.getBlockId(x, y, z);
        if (blockId == sourceBlockId) {
            return;
        }
        if (blockId == flowingBlockId) {
            if (chunk.getBlockMetadata(x, y, z) <= newHeight) {
                return;
            }
        }
        BlockHandler blockHandler = BlockHandlers.getBlockHandler(chunk.getBlockId(x, y, z));
        if (!blockHandler.isPermeable() || blockHandler.isSolid()) {
            return;
        }

        if (getSourceBlocksAdjacent(world, x, y, z) >= 2) {
            chunk.setBlock(x, y, z, sourceBlockId, (byte) 0, true);
        } else {
            chunk.setBlock(x, y, z, flowingBlockId, newHeight, true);
        }
        long nextUpdate = getTicksUntilNextUpdate(ticks, world.getFormat().getDimension());
        chunk.scheduleBlockUpdate(x, y, z, nextUpdate);
    }

    private boolean shouldUpdateThisTick(long ticks, Dimension dimension) {
        return ticks % updateFrequencyTicks.get(dimension) == 0;
    }

    private long getTicksUntilNextUpdate(long ticks, Dimension dimension) {
        long updateFrequency = updateFrequencyTicks.get(dimension);
        long difference = ticks % updateFrequency;
        return updateFrequency - difference;
    }

    private int getSourceBlocksAdjacent(World world, int x, int y, int z) {
        int sources = 0;
        Chunk chunk;

        chunk = world.getLoadedChunk((x + 1) << 4, z << 4);
        if (chunk != null) {
            sources += chunk.getBlockId(x + 1, y, z) == sourceBlockId ? 1 : 0;
        }

        chunk = world.getLoadedChunk(x << 4, (z + 1) << 4);
        if (chunk != null) {
            sources += chunk.getBlockId(x, y, z + 1) == sourceBlockId ? 1 : 0;
        }

        chunk = world.getLoadedChunk((x - 1) << 4, z << 4);
        if (chunk != null) {
            sources += chunk.getBlockId(x - 1, y, z) == sourceBlockId ? 1 : 0;
        }

        chunk = world.getLoadedChunk(x << 4, (z - 1) << 4);
        if (chunk != null) {
            sources += chunk.getBlockId(x, y, z - 1) == sourceBlockId ? 1 : 0;
        }
        return sources;
    }

}
