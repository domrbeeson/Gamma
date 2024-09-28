package domrbeeson.gamma.item.items;

import domrbeeson.gamma.item.FoodItem;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class BreadItem extends Item implements FoodItem {

    public BreadItem(int amount) {
        super(Material.BREAD, amount);
    }

    @Override
    public int getHealth() {
        return 1; // TODO
    }

}
