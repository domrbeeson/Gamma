package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.inventory.Inventory;

public abstract class InventoryTileEntity<T extends Inventory> extends TileEntity {

    private final T inv;

    public InventoryTileEntity(int x, int y, int z, T inv) {
        super(x, y, z);
        this.inv = inv;
    }

    @Override
    public void tick(long ticks) {
        inv.tick(ticks);
    }

    public final T getInventory() {
        return inv;
    }

}
