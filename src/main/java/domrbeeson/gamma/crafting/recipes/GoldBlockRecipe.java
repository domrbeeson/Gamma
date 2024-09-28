package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class GoldBlockRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { GOLD_INGOT, GOLD_INGOT, GOLD_INGOT },
            { GOLD_INGOT, GOLD_INGOT, GOLD_INGOT },
            { GOLD_INGOT, GOLD_INGOT, GOLD_INGOT }
    };

    public GoldBlockRecipe() {
        super(Material.GOLD_BLOCK.getItem(), RECIPE);
    }
}
