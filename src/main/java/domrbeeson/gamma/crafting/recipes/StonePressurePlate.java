package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class StonePressurePlate extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { STONE, STONE }
    };

    public StonePressurePlate() {
        super(Material.STONE_PRESSURE_PLATE.getItem(), RECIPE);
    }
}
