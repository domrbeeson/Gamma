package domrbeeson.gamma.item.items;

import domrbeeson.gamma.item.FoodItem;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class CookedFishItem extends Item implements FoodItem {

    public CookedFishItem(int amount) {
        super(Material.COOKED_FISH, amount);
    }

    @Override
    public int getHealth() {
        return 1; // TODO
    }

}
