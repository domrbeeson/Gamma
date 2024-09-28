package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class LadderRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { STICK, AIR, STICK },
            { STICK, STICK, STICK },
            { STICK, AIR, STICK }
    };

    public LadderRecipe() {
        super(Material.LADDER.getItem(), RECIPE);
    }
}
