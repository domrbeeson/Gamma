package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class BowlRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { ANY_PLANKS, AIR, ANY_PLANKS },
            { AIR, ANY_PLANKS, AIR }
    };

    public BowlRecipe() {
        super(Material.BOWL.getItem(4), RECIPE);
    }
}
