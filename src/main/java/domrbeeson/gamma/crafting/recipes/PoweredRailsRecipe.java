package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class PoweredRailsRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { GOLD_INGOT, AIR, GOLD_INGOT },
            { GOLD_INGOT, STICK, GOLD_INGOT },
            { GOLD_INGOT, REDSTONE, GOLD_INGOT }
    };

    public PoweredRailsRecipe() {
        super(Material.POWERED_RAIL.getItem(6), RECIPE);
    }
}
