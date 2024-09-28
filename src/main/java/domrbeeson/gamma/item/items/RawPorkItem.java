package domrbeeson.gamma.item.items;

import domrbeeson.gamma.item.FoodItem;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.item.SmeltableItem;

public class RawPorkItem extends SmeltableItem implements FoodItem {

    public RawPorkItem(int amount) {
        super(Material.RAW_PORK, amount);
    }

    @Override
    public int getHealth() {
        return 1;
    }

    @Override
    public Material getSmeltingOutput() {
        return Material.COOKED_PORK;
    }
}
