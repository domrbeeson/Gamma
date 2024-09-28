package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class SugarRecipe extends ShapelessCraftingRecipe {
    public SugarRecipe() {
        super(Material.SUGAR.getItem(), new RecipeItem(Material.SUGAR_CANE));
    }
}
