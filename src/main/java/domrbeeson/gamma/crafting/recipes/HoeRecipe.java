package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class HoeRecipe extends ShapedCraftingRecipe {

    public HoeRecipe(Material output, RecipeItem mineral) {
        super(output, new RecipeItem[][] {
                { mineral, mineral },
                { AIR, STICK },
                { AIR, STICK }
        });
    }
}
