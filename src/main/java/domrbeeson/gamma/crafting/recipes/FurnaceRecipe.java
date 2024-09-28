package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class FurnaceRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { COBBLESTONE, COBBLESTONE, COBBLESTONE },
            { COBBLESTONE, AIR, COBBLESTONE },
            { COBBLESTONE, COBBLESTONE, COBBLESTONE }
    };

    public FurnaceRecipe() {
        super(Material.FURNACE.getItem(), RECIPE);
    }
}
