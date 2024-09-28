package domrbeeson.gamma.inventory;

public abstract class ProgressBarInventory extends TileInventory {

    public ProgressBarInventory(InventoryType type, String title) {
        super(type, title);
    }

    public abstract short getProgress(short max);

}
