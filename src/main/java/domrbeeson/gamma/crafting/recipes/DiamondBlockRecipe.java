package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class DiamondBlockRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { DIAMOND, DIAMOND, DIAMOND },
            { DIAMOND, DIAMOND, DIAMOND },
            { DIAMOND, DIAMOND, DIAMOND }
    };

    public DiamondBlockRecipe() {
        super(Material.DIAMOND_BLOCK.getItem(), RECIPE);
    }
}
