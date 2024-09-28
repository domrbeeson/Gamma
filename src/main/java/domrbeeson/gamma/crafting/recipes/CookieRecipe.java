package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class CookieRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { WHEAT, new RecipeItem(Material.COCOA_BEANS), WHEAT }
    };

    public CookieRecipe() {
        super(Material.COOKIE.getItem(8), RECIPE);
    }
}
