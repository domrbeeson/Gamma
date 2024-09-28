package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class GlowstoneRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem GLOWSTONE_DUST = new RecipeItem(Material.GLOWSTONE_DUST);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { GLOWSTONE_DUST, GLOWSTONE_DUST },
            { GLOWSTONE_DUST, GLOWSTONE_DUST }
    };

    public GlowstoneRecipe() {
        super(Material.GLOWSTONE.getItem(), RECIPE);
    }
}
