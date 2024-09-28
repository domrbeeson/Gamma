package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapedCraftingRecipe;
import domrbeeson.gamma.item.Material;

public class TntRecipe extends ShapedCraftingRecipe {

    private static final RecipeItem GUNPOWDER = new RecipeItem(Material.GUNPOWDER);

    private static final RecipeItem[][] RECIPE = new RecipeItem[][] {
            { GUNPOWDER, SAND, GUNPOWDER },
            { SAND, GUNPOWDER, SAND },
            { GUNPOWDER, SAND, GUNPOWDER }
    };

    public TntRecipe() {
        super(Material.TNT.getItem(), RECIPE);
    }
}
