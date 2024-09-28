package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;

public class PickaxeRecipe extends ShapedCraftingRecipe {
    public PickaxeRecipe(Item output, RecipeItem mineral) {
        super(output, new RecipeItem[][] {
                { mineral, mineral, mineral },
                { AIR, STICK, AIR },
                { AIR, STICK, AIR }
        });
    }
}
