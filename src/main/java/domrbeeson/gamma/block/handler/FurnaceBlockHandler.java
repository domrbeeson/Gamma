package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.tile.FurnaceTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.event.events.block.BlockChangeEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FurnaceBlockHandler extends TileEntityBlockHandler<FurnaceTileEntity> {

    public FurnaceBlockHandler() {
        super(FurnaceTileEntity.class);
    }

    @Override
    public void onPlace(MinecraftServer server, BlockChangeEvent event, Chunk chunk, int x, int y, int z, byte newId, byte newMetadata, int clickedX, byte clickedY, int clickedZ, @Nullable Player player) {
        chunk.addTileEntity(new FurnaceTileEntity(chunk, x, y, z));
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        List<Item> drops = new ArrayList<>();
        drops.add(new Item(Material.FURNACE));

        TileEntity tile = chunk.getTileEntity(x, y, z);
        if (tile != null) {
            FurnaceTileEntity furnace = (FurnaceTileEntity) tile;
            drops.add(furnace.getInventory().getFuel());
            drops.add(furnace.getInventory().getOutput());
            drops.add(furnace.getInventory().getInput());
        }

        return drops;
    }

    @Override
    public boolean onRightClick(MinecraftServer server, Block block, Player player) {
        TileEntity tile = block.world().getTileEntity(block.x(), block.y(), block.z());
        if (tile instanceof FurnaceTileEntity) {
            player.openInventory(((FurnaceTileEntity) tile).getInventory());
            block.chunk().markForSaving();
        }
        return true;
    }

}
