package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.object.FallingBlockEntity;
import domrbeeson.gamma.entity.object.ObjectEntity;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.network.packet.out.BlockChangePacketOut;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class FallingBlockHandler extends BlockHandler {

    private static final byte[] FALL_INTO_BLOCKS = new byte[] {
            0, 8, 9, 10, 11
    };

    private final List<Item> drop;

    public FallingBlockHandler(Material material) {
        drop = List.of(material.getItem());
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        return drop;
    }

    @Override
    public boolean tick(MinecraftServer server, Block block, long tick) {
        int y = block.y();
        int lowestY = getLowestAvailableY(block);
        if (lowestY == block.y()) {
            return false;
        }
        Chunk chunk = block.chunk();
        int x = block.x();
        int z = block.z();
        byte relativeX = Block.getChunkRelativeX(x);
        byte relativeZ = Block.getChunkRelativeZ(z);
        chunk.directlySetBlock(relativeX, y, relativeZ, (byte) 0, (byte) 0);
        BlockChangePacketOut blockChangePacket = new BlockChangePacketOut(x, y, z, (byte) 0, (byte) 0);
//        chunk.getViewersInRange(new Pos(x, y, z), Chunk.SEND_BLOCK_UPDATE_RANGE).forEach(viewer -> viewer.player().sendPacket(blockChangePacket));
        chunk.getViewers().forEach(viewer -> viewer.sendPacket(blockChangePacket));
        // TODO spawn falling block, temporarily just placing the sand at the lowest available Y
        if (lowestY >= 0) {
            ObjectEntity entity;
            if (block.id() == 12) {
                entity = new FallingBlockEntity(block.world(), new Pos(x, y, z), Material.SAND.blockId);
            } else {
                entity = new FallingBlockEntity(block.world(), new Pos(x, y, z), Material.GRAVEL.blockId);
            }
            entity.spawn();
            chunk.getWorld().getScheduler().runNextTick(ticks -> entity.remove());
        }
        return true;
    }

    private int getLowestAvailableY(Block block) {
        byte relativeX = Block.getChunkRelativeX(block.x());
        byte relativeZ = Block.getChunkRelativeZ(block.z());
        Chunk chunk = block.chunk();
        int lowestY = block.y();
        for (int y = lowestY - 1; y >= 0; y--) {
            if (canFall(chunk.getBlockId(relativeX, y, relativeZ))) {
                lowestY = y;
            } else {
                break;
            }
        }
        return lowestY;
    }

    private boolean canFall(byte blockId) {
        for (byte id : FALL_INTO_BLOCKS) {
            if (id == blockId) {
                return true;
            }
        }
        return false;
    }

}
