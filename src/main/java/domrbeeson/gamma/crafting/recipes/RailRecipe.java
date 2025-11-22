package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class RailRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { IRON_INGOT, AIR, IRON_INGOT },
            { IRON_INGOT, STICK, IRON_INGOT },
            { IRON_INGOT, AIR, IRON_INGOT }
    };

    public RailRecipe() {
        super(new Item(Material.RAIL, 16), RECIPE);
    }
}
