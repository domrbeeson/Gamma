package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class DispenserRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { COBBLESTONE, COBBLESTONE, COBBLESTONE },
            { COBBLESTONE, new RecipeItem(Material.BOW), COBBLESTONE },
            { COBBLESTONE, REDSTONE, COBBLESTONE }
    };

    public DispenserRecipe() {
        super(Material.DISPENSER.getItem(), RECIPE);
    }
}
