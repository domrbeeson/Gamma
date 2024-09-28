package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class SnowBlockRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem SNOWBALL = new RecipeItem(Material.SNOWBALL);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { SNOWBALL, SNOWBALL },
            { SNOWBALL, SNOWBALL }
    };

    public SnowBlockRecipe() {
        super(Material.SNOW_BLOCK.getItem(), RECIPE);
    }
}
