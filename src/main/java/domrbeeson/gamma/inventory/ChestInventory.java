package domrbeeson.gamma.inventory;

public class ChestInventory extends TileInventory {

    public ChestInventory(int rows) {
        super(InventoryType.values()[rows], "Chest");
    }

}
