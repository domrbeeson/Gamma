package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.BlockHandlers;
import domrbeeson.gamma.block.tile.SignTileEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.event.events.block.BlockChangeEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SignBlockHandler extends TileEntityBlockHandler<SignTileEntity> {

    private static final List<Item> DROPS = List.of(new Item(Material.SIGN));

    public SignBlockHandler() {
        super(SignTileEntity.class);
    }

    @Override
    public void onPlace(MinecraftServer server, BlockChangeEvent event, Chunk chunk, int x, int y, int z, byte newId, byte newMetadata, int clickedX, byte clickedY, int clickedZ, @Nullable Player player) {
        chunk.addTileEntity(new SignTileEntity(chunk, x, y, z));

        if (player == null) {
            return;
        }

        if (clickedZ > z) {
            event.setNewId(Material.WALL_SIGN.blockId);
            event.setNewMetadata(Direction.NORTH.getMetadata());
        } else if (clickedZ < z) {
            event.setNewId(Material.WALL_SIGN.blockId);
            event.setNewMetadata(Direction.SOUTH.getMetadata());
        } else if (clickedX > x) {
            event.setNewId(Material.WALL_SIGN.blockId);
            event.setNewMetadata(Direction.WEST.getMetadata());
        } else if (clickedX < x) {
            event.setNewId(Material.WALL_SIGN.blockId);
            event.setNewMetadata(Direction.EAST.getMetadata());
        } else {
            event.setNewId(Material.SIGN_POST.blockId);
            byte meta = (byte) Math.floor((player.getPos().yaw() + 180f) / 22.5 + 0.5);
            server.broadcast("sign rotation: " + meta);
            event.setNewMetadata(meta);
        }

        player.setEditingSign(new Pos(x, y, z));
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte blockId, byte blockMetadata, short toolId) {
        return DROPS;
    }

    @Override
    public boolean update(MinecraftServer server, Block block, long tick) {
        Chunk chunk = block.chunk();
        int x = block.x();
        int y = block.y();
        int z = block.z();
        switch (block.material()) {
            case SIGN_POST -> {
                if (!BlockHandlers.getBlockHandler(chunk.getBlockId(x, y - 1, z)).isSolid()) {
                    chunk.breakBlock(x, y, z);
                    return true;
                }
            }
            case WALL_SIGN -> {
                switch (Direction.getDirectionFromMetadata(block.metadata())) { // Could just use numbers but getting the Direction is more readable
                    case SOUTH:
                        if (BlockHandlers.getBlockHandler(chunk.getBlockId(x, y, z - 1)).isSolid()) {
                            chunk.breakBlock(x, y, z);
                            return true;
                        }
                        break;
                    case WEST:
                        if (BlockHandlers.getBlockHandler(chunk.getBlockId(x + 1, y, z)).isSolid()) {
                            chunk.breakBlock(x, y, z);
                            return true;
                        }
                        break;
                    case EAST:
                        if (BlockHandlers.getBlockHandler(chunk.getBlockId(x - 1, y, z)).isSolid()) {
                            chunk.breakBlock(x, y, z);
                            return true;
                        }
                        break;
                    default:
                        if (BlockHandlers.getBlockHandler(chunk.getBlockId(x, y, z + 1)).isSolid()) {
                            chunk.breakBlock(x, y, z);
                            return true;
                        }
                        break;
                }
            }
        }
        return false;
    }

    public enum Direction {
        NORTH(2),
        SOUTH(3),
        WEST(4),
        EAST(5),
        ;

        private final byte metadata;

        Direction(int metadata) {
            this.metadata = (byte) metadata;
        }

        public byte getMetadata() {
            return metadata;
        }

        public static Direction getDirectionFromMetadata(byte metadata) {
            if (metadata - 2 > values().length || metadata < 0) {
                return NORTH;
            }
            return values()[metadata - 2];
        }
    }

}
