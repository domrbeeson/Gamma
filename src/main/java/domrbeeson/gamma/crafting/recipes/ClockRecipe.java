package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class ClockRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { AIR, GOLD_INGOT, AIR },
            { GOLD_INGOT, REDSTONE, GOLD_INGOT },
            { AIR, GOLD_INGOT, AIR }
    };

    public ClockRecipe() {
        super(Material.CLOCK.getItem(), RECIPE);
    }
}
