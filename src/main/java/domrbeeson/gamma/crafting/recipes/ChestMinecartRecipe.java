package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class ChestMinecartRecipe extends ShapelessCraftingRecipe {
    public ChestMinecartRecipe() {
        super(Material.CHEST_MINECART.getItem(), new RecipeItem(Material.CHEST), MINECART);
    }
}
