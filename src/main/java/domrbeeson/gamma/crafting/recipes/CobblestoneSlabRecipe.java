package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class CobblestoneSlabRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { COBBLESTONE, COBBLESTONE, COBBLESTONE }
    };

    public CobblestoneSlabRecipe() {
        super(new Item(Material.COBBLESTONE_SLAB, 3), RECIPE);
    }
}
