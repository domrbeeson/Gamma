package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class PaintingRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { STRING, STRING, STRING },
            { STRING, ANY_WOOL, STRING },
            { STRING, STRING, STRING }
    };

    public PaintingRecipe() {
        super(Material.PAINTING.getItem(), RECIPE);
    }
}
