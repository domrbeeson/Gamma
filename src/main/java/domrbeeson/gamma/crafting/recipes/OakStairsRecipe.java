package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class OakStairsRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { OAK_PLANKS },
            { OAK_PLANKS, OAK_PLANKS },
            { OAK_PLANKS, OAK_PLANKS, OAK_PLANKS }
    };

    public OakStairsRecipe() {
        super(Material.OAK_STAIRS.getItem(), RECIPE);
    }
}
