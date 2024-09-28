package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class MinecartRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { IRON_INGOT, AIR, IRON_INGOT },
            { IRON_INGOT, IRON_INGOT, IRON_INGOT }
    };

    public MinecartRecipe() {
        super(Material.MINECART.getItem(), RECIPE);
    }
}
