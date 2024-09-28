package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class OakSlabRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { OAK_PLANKS, OAK_PLANKS, OAK_PLANKS }
    };

    public OakSlabRecipe() {
        super(Material.OAK_SLAB.getItem(3), RECIPE);
    }
}
