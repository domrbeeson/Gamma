package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class CompassRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { AIR, IRON_INGOT, AIR },
            { IRON_INGOT, REDSTONE, IRON_INGOT },
            { AIR, IRON_INGOT, AIR }
    };

    public CompassRecipe() {
        super(Material.COMPASS.getItem(), RECIPE);
    }
}
