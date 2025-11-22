package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class ShovelRecipe extends ShapedCraftingRecipe {
    public ShovelRecipe(Material output, RecipeItem mineral) {
        super(output, new RecipeItem[][] {
                { mineral },
                { STICK },
                { STICK }
        });
    }
}
