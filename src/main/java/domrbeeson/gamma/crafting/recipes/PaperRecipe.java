package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class PaperRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem SUGAR_CANE = new RecipeItem(Material.SUGAR_CANE);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { SUGAR_CANE, SUGAR_CANE, SUGAR_CANE }
    };

    public PaperRecipe() {
        super(Material.PAPER.getItem(), RECIPE);
    }
}
