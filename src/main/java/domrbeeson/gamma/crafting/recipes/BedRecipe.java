package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class BedRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { ANY_WOOL, ANY_WOOL, ANY_WOOL },
            { ANY_PLANKS, ANY_PLANKS, ANY_PLANKS }
    };

    public BedRecipe() {
        super(Material.BED.getItem(), RECIPE);
    }
}
