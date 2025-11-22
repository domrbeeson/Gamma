package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class OakSlabRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { OAK_PLANKS, OAK_PLANKS, OAK_PLANKS }
    };

    public OakSlabRecipe() {
        super(new Item(Material.OAK_SLAB, 3), RECIPE);
    }
}
