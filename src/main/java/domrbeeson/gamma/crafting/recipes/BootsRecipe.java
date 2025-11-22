package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class BootsRecipe extends ShapedCraftingRecipe {
    public BootsRecipe(Material output, RecipeItem material) {
        super(output, new RecipeItem[][] {
                { material, AIR, material },
                { material, AIR, material }
        });
    }
}
