package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class RedstoneTorchRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { REDSTONE },
            { STICK }
    };

    public RedstoneTorchRecipe() {
        super(Material.REDSTONE_TORCH.getItem(), RECIPE);
    }
}
