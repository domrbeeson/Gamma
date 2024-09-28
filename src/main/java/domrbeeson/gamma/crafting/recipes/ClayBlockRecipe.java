package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class ClayBlockRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem CLAY = new RecipeItem(Material.CLAY);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { CLAY, CLAY },
            { CLAY, CLAY }
    };

    public ClayBlockRecipe() {
        super(Material.CLAY_BLOCK.getItem(), RECIPE);
    }
}
