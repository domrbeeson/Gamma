package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class BookshelfRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem BOOK = new RecipeItem(Material.BOOK);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { ANY_PLANKS, ANY_PLANKS, ANY_PLANKS },
            { BOOK, BOOK, BOOK },
            { ANY_PLANKS, ANY_PLANKS, ANY_PLANKS }
    };

    public BookshelfRecipe() {
        super(Material.BOOKSHELF.getItem(), RECIPE);
    }
}
