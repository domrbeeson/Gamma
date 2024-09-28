package domrbeeson.gamma.item.items;

import domrbeeson.gamma.item.FoodItem;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class NotchAppleItem extends Item implements FoodItem {

    public NotchAppleItem(int amount) {
        super(Material.NOTCH_APPLE, amount);
    }

    @Override
    public int getHealth() {
        return 1; // TODO
    }

    // TODO does notch apple have extra effects in beta?
}
