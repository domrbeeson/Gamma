package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;

public class LeggingsRecipe extends ShapedCraftingRecipe {
    public LeggingsRecipe(Item output, RecipeItem material) {
        super(output, new RecipeItem[][] {
                { material, material, material },
                { material, AIR, material },
                { material, AIR, material }
        });
    }
}
