package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class ShearsRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { AIR, IRON_INGOT },
            { IRON_INGOT, AIR }
    };

    public ShearsRecipe() {
        super(Material.SHEARS.getItem(), RECIPE);
    }
}
