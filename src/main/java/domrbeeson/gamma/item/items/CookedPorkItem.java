package domrbeeson.gamma.item.items;

import domrbeeson.gamma.item.FoodItem;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class CookedPorkItem extends Item implements FoodItem {

    public CookedPorkItem(int amount) {
        super(Material.COOKED_PORK, amount);
    }

    @Override
    public int getHealth() {
        return 1; // TODO
    }
}
