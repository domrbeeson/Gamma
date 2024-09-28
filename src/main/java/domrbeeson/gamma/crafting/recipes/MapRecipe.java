package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class MapRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { PAPER, PAPER, PAPER },
            { PAPER, new RecipeItem(Material.COMPASS), PAPER },
            { PAPER, PAPER, PAPER }
    };

    public MapRecipe() {
        super(Material.MAP.getItem(), RECIPE);
    }
}
