package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class FlintAndSteelRecipe extends ShapelessCraftingRecipe {
    public FlintAndSteelRecipe() {
        super(Material.FLINT_AND_STEEL.getItem(), IRON_INGOT, FLINT);
    }
}
