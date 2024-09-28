package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.inventory.FurnaceInventory;

public class FurnaceTileEntity extends InventoryTileEntity<FurnaceInventory> {

    public FurnaceTileEntity(int x, int y, int z) {
        super(x, y, z, new FurnaceInventory());
    }

}
