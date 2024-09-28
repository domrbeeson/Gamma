package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class StoneSlabRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { STONE, STONE, STONE }
    };

    public StoneSlabRecipe() {
        super(Material.STONE_SLAB.getItem(3), RECIPE);
    }
}
