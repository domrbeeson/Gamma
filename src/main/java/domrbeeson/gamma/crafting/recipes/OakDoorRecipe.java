package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class OakDoorRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { ANY_PLANKS, ANY_PLANKS },
            { ANY_PLANKS, ANY_PLANKS },
            { ANY_PLANKS, ANY_PLANKS }
    };

    public OakDoorRecipe() {
        super(Material.OAK_DOOR.getItem(), RECIPE);
    }
}
