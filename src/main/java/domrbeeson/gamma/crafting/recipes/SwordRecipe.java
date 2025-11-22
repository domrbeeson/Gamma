package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class SwordRecipe extends ShapedCraftingRecipe {
    public SwordRecipe(Material output, RecipeItem mineral) {
        super(output, new RecipeItem[][] {
                { mineral },
                { mineral },
                { STICK }
        });
    }
}
