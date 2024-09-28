package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class BrickBlockRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem BRICK = new RecipeItem(Material.BRICK);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { BRICK, BRICK },
            { BRICK, BRICK }
    };

    public BrickBlockRecipe() {
        super(Material.BRICK_BLOCK.getItem(), RECIPE);
    }
}
