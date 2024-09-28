package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class StickyPistonRecipe extends ShapelessCraftingRecipe {

    public StickyPistonRecipe() {
        super(Material.STICKY_PISTON.getItem(), new RecipeItem(Material.PISTON), new RecipeItem(Material.SLIME_BALL));
    }
}
