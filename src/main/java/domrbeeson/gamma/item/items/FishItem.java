package domrbeeson.gamma.item.items;

import domrbeeson.gamma.item.FoodItem;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.item.SmeltableItem;

public class FishItem extends SmeltableItem implements FoodItem {

    public FishItem(int amount) {
        super(Material.FISH, amount);
    }

    @Override
    public int getHealth() {
        return 1; // TODO
    }

    @Override
    public Material getSmeltingOutput() {
        return Material.COOKED_FISH;
    }
}
