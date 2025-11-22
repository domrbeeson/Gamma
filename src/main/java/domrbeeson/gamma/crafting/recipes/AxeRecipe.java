package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class AxeRecipe extends ShapedCraftingRecipe {
    public AxeRecipe(Material output, RecipeItem mineral) {
        super(output, new RecipeItem[][] {
                { mineral, mineral },
                { mineral, STICK },
                { AIR, STICK }
        });
    }
}
