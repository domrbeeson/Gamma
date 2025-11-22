package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.inventory.DispenserInventory;
import domrbeeson.gamma.world.Chunk;

public class DispenserTileEntity extends InventoryTileEntity<DispenserInventory> {

    public DispenserTileEntity(Chunk chunk, int x, int y, int z) {
        this(chunk, x, y, z, new DispenserInventory());
    }

    public DispenserTileEntity(Chunk chunk, int x, int y, int z, DispenserInventory inv) {
        super(chunk, x, y, z, inv);
    }

}
