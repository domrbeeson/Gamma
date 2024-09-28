package domrbeeson.gamma.inventory;

import domrbeeson.gamma.item.Item;
import org.jetbrains.annotations.Nullable;

public abstract class TileInventory extends Inventory {

    public TileInventory(InventoryType type, String title) {
        super(type, title);
    }

    @Override
    public boolean setSlot(int slot, @Nullable Item item, boolean update) {
        boolean updated = super.setSlot(slot, item, update);
        // TODO how do I get the chunk here?
//        if (updated) {
//            chunk.markForSaving();
//        }
        return updated;
    }

}
