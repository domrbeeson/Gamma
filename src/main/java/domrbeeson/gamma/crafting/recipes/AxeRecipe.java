package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;

public class AxeRecipe extends ShapedCraftingRecipe {
    public AxeRecipe(Item output, RecipeItem mineral) {
        super(output, new RecipeItem[][] {
                { mineral, mineral },
                { mineral, STICK },
                { AIR, STICK }
        });
    }
}
