package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;

public class SwordRecipe extends ShapedCraftingRecipe {
    public SwordRecipe(Item output, RecipeItem mineral) {
        super(output, new RecipeItem[][] {
                { mineral },
                { mineral },
                { STICK }
        });
    }
}
