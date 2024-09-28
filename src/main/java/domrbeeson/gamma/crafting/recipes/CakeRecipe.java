package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class CakeRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem MILK_BUCKET = new RecipeItem(Material.MILK_BUCKET, Material.BUCKET);
    private static final RecipeItem SUGAR = new RecipeItem(Material.SUGAR);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { MILK_BUCKET, MILK_BUCKET, MILK_BUCKET },
            { SUGAR, new RecipeItem(Material.EGG), SUGAR },
            { WHEAT, WHEAT, WHEAT }
    };

    public CakeRecipe() {
        super(Material.CAKE.getItem(), RECIPE);
    }
}
