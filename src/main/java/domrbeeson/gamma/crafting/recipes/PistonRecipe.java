package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class PistonRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { ANY_PLANKS, ANY_PLANKS, ANY_PLANKS },
            { COBBLESTONE, IRON_INGOT, COBBLESTONE },
            { COBBLESTONE, REDSTONE, COBBLESTONE }
    };

    public PistonRecipe() {
        super(Material.PISTON.getItem(), RECIPE);
    }
}
