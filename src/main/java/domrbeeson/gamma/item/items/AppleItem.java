package domrbeeson.gamma.item.items;

import domrbeeson.gamma.item.FoodItem;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class AppleItem extends Item implements FoodItem {

    public AppleItem(int amount) {
        super(Material.APPLE, amount);
    }

    @Override
    public int getHealth() {
        return 1;
    }

}
