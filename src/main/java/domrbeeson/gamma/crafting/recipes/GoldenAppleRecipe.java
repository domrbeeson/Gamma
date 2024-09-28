package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class GoldenAppleRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem GOLD_BLOCK = new RecipeItem(Material.GOLD_BLOCK);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { GOLD_BLOCK, GOLD_BLOCK, GOLD_BLOCK },
            { GOLD_BLOCK, new RecipeItem(Material.APPLE), GOLD_BLOCK },
            { GOLD_BLOCK, GOLD_BLOCK, GOLD_BLOCK }
    };

    public GoldenAppleRecipe() {
        super(Material.GOLDEN_APPLE.getItem(), RECIPE);
    }
}
