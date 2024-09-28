package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class BookRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { PAPER, PAPER },
            { PAPER, new RecipeItem(Material.LEATHER) }
    };

    public BookRecipe() {
        super(Material.BOOK.getItem(), RECIPE);
    }
}
