package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;

public class ShovelRecipe extends ShapedCraftingRecipe {
    public ShovelRecipe(Item output, RecipeItem mineral) {
        super(output, new RecipeItem[][] {
                { mineral },
                { STICK },
                { STICK }
        });
    }
}
