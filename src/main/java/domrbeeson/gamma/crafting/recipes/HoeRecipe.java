package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;

public class HoeRecipe extends ShapedCraftingRecipe {

    public HoeRecipe(Item output, RecipeItem mineral) {
        super(output, new RecipeItem[][] {
                { mineral, mineral },
                { AIR, STICK },
                { AIR, STICK }
        });
    }
}
