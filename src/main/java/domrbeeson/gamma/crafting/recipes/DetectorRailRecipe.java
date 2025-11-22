package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class DetectorRailRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { IRON_INGOT, AIR, IRON_INGOT },
            { IRON_INGOT, new RecipeItem(Material.STONE_PRESSURE_PLATE), IRON_INGOT },
            { IRON_INGOT, REDSTONE, IRON_INGOT }
    };

    public DetectorRailRecipe() {
        super(new Item(Material.DETECTOR_RAIL, 6), RECIPE);
    }
}
