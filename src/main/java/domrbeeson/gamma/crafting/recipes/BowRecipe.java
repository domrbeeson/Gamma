package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class BowRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { AIR, STICK, STRING },
            { STICK, AIR, STRING },
            { AIR, STICK, STRING }
    };

    public BowRecipe() {
        super(Material.BOW.getItem(), RECIPE);
    }
}
