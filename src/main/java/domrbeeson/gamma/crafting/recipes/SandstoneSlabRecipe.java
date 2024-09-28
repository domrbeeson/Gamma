package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class SandstoneSlabRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem SANDSTONE = new RecipeItem(Material.SANDSTONE);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { SANDSTONE, SANDSTONE, SANDSTONE }
    };

    public SandstoneSlabRecipe() {
        super(Material.SANDSTONE_SLAB.getItem(3), RECIPE);
    }
}
