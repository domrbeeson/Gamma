package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;

public class TunicRecipe extends ShapedCraftingRecipe {
    public TunicRecipe(Item output, RecipeItem material) {
        super(output, new RecipeItem[][] {
                { material, AIR, material },
                { material, material, material },
                { material, material, material }
        });
    }
}
