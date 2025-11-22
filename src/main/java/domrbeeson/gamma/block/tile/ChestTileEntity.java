package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.inventory.ChestInventory;
import domrbeeson.gamma.inventory.InventoryType;
import domrbeeson.gamma.world.Chunk;

public class ChestTileEntity extends InventoryTileEntity<ChestInventory> {

    public ChestTileEntity(Chunk chunk, int x, int y, int z) {
        this(chunk, x, y, z, new ChestInventory(InventoryType.CHEST_3_ROWS.ordinal()));
    }

    public ChestTileEntity(Chunk chunk, int x, int y, int z, ChestInventory inv) {
        super(chunk, x, y, z, inv);
    }

}
