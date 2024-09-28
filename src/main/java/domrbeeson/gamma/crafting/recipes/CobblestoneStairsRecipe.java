package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class CobblestoneStairsRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { COBBLESTONE },
            { COBBLESTONE, COBBLESTONE },
            { COBBLESTONE, COBBLESTONE, COBBLESTONE }
    };

    public CobblestoneStairsRecipe() {
        super(Material.COBBLESTONE_STAIRS.getItem(), RECIPE);
    }
}
