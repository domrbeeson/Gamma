package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class ChestRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { ANY_PLANKS, ANY_PLANKS, ANY_PLANKS },
            { ANY_PLANKS, AIR, ANY_PLANKS },
            { ANY_PLANKS, ANY_PLANKS, ANY_PLANKS }
    };

    public ChestRecipe() {
        super(Material.CHEST.getItem(), RECIPE);
    }

}