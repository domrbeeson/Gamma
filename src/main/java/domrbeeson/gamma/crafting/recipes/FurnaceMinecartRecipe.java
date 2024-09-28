package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class FurnaceMinecartRecipe extends ShapelessCraftingRecipe {
    public FurnaceMinecartRecipe() {
        super(Material.FURNACE_MINECART.getItem(), new RecipeItem(Material.FURNACE), MINECART);
    }
}
