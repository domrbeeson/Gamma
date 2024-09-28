package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class ArrowRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { FLINT },
            { STICK },
            { new RecipeItem(Material.FEATHER) }
    };

    public ArrowRecipe() {
        super(Material.ARROW.getItem(), RECIPE);
    }
}
