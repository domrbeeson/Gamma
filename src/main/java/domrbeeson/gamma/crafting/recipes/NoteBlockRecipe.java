package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class NoteBlockRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { ANY_PLANKS, ANY_PLANKS, ANY_PLANKS},
            { ANY_PLANKS, REDSTONE, ANY_PLANKS },
            { ANY_PLANKS, ANY_PLANKS, ANY_PLANKS }
    };

    public NoteBlockRecipe() {
        super(Material.NOTE_BLOCK.getItem(), RECIPE);
    }
}
