package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class BoneMealRecipe extends ShapelessCraftingRecipe {
    public BoneMealRecipe() {
        super(Material.BONE_MEAL.getItem(3), new RecipeItem(Material.BONE));
    }
}
