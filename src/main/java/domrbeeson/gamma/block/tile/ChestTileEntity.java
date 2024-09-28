package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.inventory.ChestInventory;
import domrbeeson.gamma.inventory.InventoryType;

public class ChestTileEntity extends InventoryTileEntity<ChestInventory> {

    public ChestTileEntity(int x, int y, int z) {
        this(x, y, z, new ChestInventory(InventoryType.CHEST_3_ROWS.ordinal()));
    }

    public ChestTileEntity(int x, int y, int z, ChestInventory inv) {
        super(x, y, z, inv);
    }

}
