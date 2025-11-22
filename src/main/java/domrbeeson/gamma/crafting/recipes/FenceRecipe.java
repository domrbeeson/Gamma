package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class FenceRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { STICK, STICK, STICK },
            { STICK, STICK, STICK }
    };

    public FenceRecipe() {
        super(new Item(Material.FENCE, 2), RECIPE);
    }
}
