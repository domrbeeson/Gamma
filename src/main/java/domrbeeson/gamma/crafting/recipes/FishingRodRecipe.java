package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class FishingRodRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { AIR, AIR, STICK },
            { AIR, STICK, STRING },
            { STICK, AIR, STRING }
    };

    public FishingRodRecipe() {
        super(Material.FISHING_ROD.getItem(), RECIPE);
    }
}
