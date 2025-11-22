package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class TunicRecipe extends ShapedCraftingRecipe {
    public TunicRecipe(Material output, RecipeItem material) {
        super(output, new RecipeItem[][] {
                { material, AIR, material },
                { material, material, material },
                { material, material, material }
        });
    }
}
