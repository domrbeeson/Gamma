package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class JackOLanternRecipe extends ShapelessCraftingRecipe {
    public JackOLanternRecipe() {
        super(Material.JACK_O_LANTERN.getItem(), new RecipeItem(Material.PUMPKIN), new RecipeItem(Material.TORCH));
    }
}
