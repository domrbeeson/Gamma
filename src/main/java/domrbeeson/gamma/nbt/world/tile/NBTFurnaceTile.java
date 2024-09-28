package domrbeeson.gamma.nbt.world.tile;

import domrbeeson.gamma.block.tile.FurnaceTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.inventory.FurnaceInventory;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTShort;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTFurnaceTile extends NBTInventoryTile {

    private final FurnaceInventory inventory;

    public NBTFurnaceTile(NBTCompound compound) {
        super(compound);
        inventory = new FurnaceInventory();
    }

    public NBTFurnaceTile(TileEntity tileEntity) {
        super(FURNACE_NAME, tileEntity);
        if (tileEntity instanceof FurnaceTileEntity furnace) {
            inventory = furnace.getInventory();
        } else {
            inventory = new FurnaceInventory();
        }
    }

    @Override
    public TileEntity createTileEntity(World world) {
        return new FurnaceTileEntity(getX(), getY(), getZ());
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        // TODO get actual values from furnace inventory
        tags.put("BurnTime", new NBTShort((short) 0));
        tags.put("CookTime", new NBTShort((short) 0));

        return tags;
    }

}
