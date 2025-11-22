package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class BoneMealRecipe extends ShapelessCraftingRecipe {
    public BoneMealRecipe() {
        super(new Item(Material.BONE_MEAL, 3), new RecipeItem(Material.BONE));
    }
}
