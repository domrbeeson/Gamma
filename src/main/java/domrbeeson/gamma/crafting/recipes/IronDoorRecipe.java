package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class IronDoorRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { IRON_INGOT, IRON_INGOT },
            { IRON_INGOT, IRON_INGOT },
            { IRON_INGOT, IRON_INGOT }
    };

    public IronDoorRecipe() {
        super(Material.IRON_DOOR.getItem(), RECIPE);
    }
}
