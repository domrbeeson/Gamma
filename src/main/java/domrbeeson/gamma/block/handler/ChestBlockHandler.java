package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.BlockHandlers;
import domrbeeson.gamma.block.tile.ChestTileEntity;
import domrbeeson.gamma.event.events.block.BlockChangeEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChestBlockHandler extends TileEntityBlockHandler<ChestTileEntity> {

    public ChestBlockHandler() {
        super(ChestTileEntity.class);
    }

    @Override
    public void onPlace(MinecraftServer server, BlockChangeEvent event, Chunk chunk, int x, int y, int z, byte newId, byte newMetadata, int clickedX, byte clickedY, int clickedZ, @Nullable Player player) {
        chunk.addTileEntity(new ChestTileEntity(chunk, x, y, z));
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        List<Item> drops = new ArrayList<>();
        drops.add(new Item(Material.CHEST));
        ChestTileEntity tile = getTileEntity(chunk, x, y, z);
        if (tile != null) {
            for (Item item : tile.getInventory().getSlots()) {
                if (item == null || item.getId() == 0) {
                    continue;
                }
                drops.add(item);
            }
        }
        return drops;
    }

    @Override
    public boolean onRightClick(MinecraftServer server, Block block, Player player) {
        if (BlockHandlers.getBlockHandler(block.chunk().getBlockId(block.x(), block.y() + 1, block.z())).isSolid()) {
            return false;
        }
        ChestTileEntity tile = getTileEntity(block.chunk(), block.x(), block.y(), block.z());
        if (tile != null) {
            player.openInventory(tile.getInventory());
            block.chunk().markForSaving(); // This is a workaround - instead of marking the chunk for saving if the inventory actually changes, do it on open, because the chunk cannot be stored in NBTChestTile
        }
        return true;
    }

}
