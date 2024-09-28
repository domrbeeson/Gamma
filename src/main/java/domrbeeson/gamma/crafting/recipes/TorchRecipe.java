package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class TorchRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem COAL_CHARCOAL = new RecipeItem(Material.COAL.id);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { COAL_CHARCOAL },
            { STICK }
    };

    public TorchRecipe() {
        super(Material.TORCH.getItem(4), RECIPE);
    }
}
