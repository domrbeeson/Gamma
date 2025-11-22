package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.ItemHandler;
import domrbeeson.gamma.item.Material;

public class OakPlanksRecipe extends ShapelessCraftingRecipe {

    private static final Item OUTPUT = new Item(Material.OAK_PLANKS, 4);

    public OakPlanksRecipe() {
        this(new RecipeItem(Material.OAK_LOG));
    }

    public OakPlanksRecipe(RecipeItem... log) {
        super(OUTPUT, log);
    }

}
