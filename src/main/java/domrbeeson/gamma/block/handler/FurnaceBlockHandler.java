package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.tile.FurnaceTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;

import java.util.ArrayList;
import java.util.List;

public class FurnaceBlockHandler extends TileEntityBlockHandler<FurnaceTileEntity> {

    public FurnaceBlockHandler() {
        super(FurnaceTileEntity.class);
    }

    @Override
    public void onPlace(MinecraftServer server, Block block, Player player) {
        block.chunk().addTileEntity(new FurnaceTileEntity(block.x(), block.y(), block.z()));
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        List<Item> drops = new ArrayList<>();
        drops.add(Material.FURNACE.getItem());
        // TODO drop furnace items
        return drops;
    }

    @Override
    public boolean onRightClick(MinecraftServer server, Block block, Player player) {
        TileEntity tile = block.chunk().getTileEntity(block.x(), block.y(), block.z());
        if (tile instanceof FurnaceTileEntity) {
            player.openInventory(((FurnaceTileEntity) tile).getInventory());
            block.chunk().markForSaving();
        }
        return true;
    }

}
