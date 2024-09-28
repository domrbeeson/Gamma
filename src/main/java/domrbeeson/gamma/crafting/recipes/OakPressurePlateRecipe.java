package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class OakPressurePlateRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { OAK_PLANKS, OAK_PLANKS }
    };

    public OakPressurePlateRecipe() {
        super(Material.OAK_PRESSURE_PLATE.getItem(), RECIPE);
    }
}
