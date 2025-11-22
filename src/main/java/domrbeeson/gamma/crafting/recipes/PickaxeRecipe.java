package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class PickaxeRecipe extends ShapedCraftingRecipe {
    public PickaxeRecipe(Material output, RecipeItem mineral) {
        super(output, new RecipeItem[][] {
                { mineral, mineral, mineral },
                { AIR, STICK, AIR },
                { AIR, STICK, AIR }
        });
    }
}
