package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class SandstoneRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { SAND, SAND },
            { SAND, SAND }
    };

    public SandstoneRecipe() {
        super(Material.SANDSTONE.getItem(), RECIPE);
    }

}
