package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class BucketRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { IRON_INGOT, AIR, IRON_INGOT },
            { AIR, IRON_INGOT, AIR }
    };

    public BucketRecipe() {
        super(Material.BUCKET.getItem(), RECIPE);
    }
}
