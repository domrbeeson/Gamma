package domrbeeson.gamma.item.items;

import domrbeeson.gamma.item.FoodItem;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class GoldenAppleItem extends Item implements FoodItem {

    public GoldenAppleItem(int amount) {
        super(Material.GOLDEN_APPLE, amount);
    }

    @Override
    public int getHealth() {
        return 1;
    }

    // TODO does golden apple have extra effects in beta?
}
