package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;

public class BootsRecipe extends ShapedCraftingRecipe {
    public BootsRecipe(Item output, RecipeItem material) {
        super(output, new RecipeItem[][] {
                { material, AIR, material },
                { material, AIR, material }
        });
    }
}
