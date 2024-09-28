package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class RedstoneRepeaterRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem REDSTONE_TORCH = new RecipeItem(Material.REDSTONE_TORCH);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { REDSTONE_TORCH, REDSTONE, REDSTONE_TORCH },
            { STONE, STONE, STONE }
    };

    public RedstoneRepeaterRecipe() {
        super(Material.REDSTONE_REPEATER.getItem(), RECIPE);
    }
}
