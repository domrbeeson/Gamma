package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class BreadRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { WHEAT, WHEAT, WHEAT }
    };

    public BreadRecipe() {
        super(Material.BREAD.getItem(), RECIPE);
    }
}
