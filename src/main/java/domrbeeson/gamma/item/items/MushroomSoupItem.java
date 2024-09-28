package domrbeeson.gamma.item.items;

import domrbeeson.gamma.item.FoodItem;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class MushroomSoupItem extends Item implements FoodItem {

    public MushroomSoupItem(int amount) {
        super(Material.MUSHROOM_SOUP, amount);
    }

    @Override
    public int getHealth() {
        return 1; // TODO
    }

    @Override
    public Material getItemAfterUse() {
        return Material.BOWL;
    }
}
