package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class MushroomSoupRecipe extends ShapelessCraftingRecipe {
    public MushroomSoupRecipe() {
        super(Material.MUSHROOM_SOUP.getItem(), new RecipeItem(Material.BOWL), new RecipeItem(Material.RED_MUSHROOM), new RecipeItem(Material.BROWN_MUSHROOM));
    }
}
