package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.tile.ChestTileEntity;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;

import java.util.ArrayList;
import java.util.List;

public class ChestBlockHandler extends TileEntityBlockHandler<ChestTileEntity> {

    public ChestBlockHandler() {
        super(ChestTileEntity.class);
    }

    @Override
    public void onPlace(MinecraftServer server, Block block, Player player) {
        block.chunk().addTileEntity(new ChestTileEntity(block.x(), block.y(), block.z()));
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        List<Item> drops = new ArrayList<>();
        drops.add(Material.CHEST.getItem());
        ChestTileEntity tile = getTileEntity(chunk, x, y, z);
        if (tile != null) {
            for (Item item : tile.getInventory().getSlots()) {
                if (item == null || item.id() == 0) {
                    continue;
                }
                drops.add(item);
            }
        }
        return drops;
    }

    @Override
    public boolean onRightClick(MinecraftServer server, Block block, Player player) {
        if (server.getBlockHandlers().get(block.chunk().getBlock(block.x(), block.y() + 1, block.z()).id()).isSolid()) {
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
