package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.inventory.DispenserInventory;

public class DispenserTileEntity extends InventoryTileEntity<DispenserInventory> {

    public DispenserTileEntity(int x, int y, int z) {
        this(x, y, z, new DispenserInventory());
    }

    public DispenserTileEntity(int x, int y, int z, DispenserInventory inv) {
        super(x, y, z, inv);
    }

}
