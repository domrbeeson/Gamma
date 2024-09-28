package domrbeeson.gamma.crafting.recipes;

import domrbeeson.gamma.crafting.RecipeItem;
import domrbeeson.gamma.crafting.ShapelessCraftingRecipe;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class OakPlanksRecipe extends ShapelessCraftingRecipe {

    public static final Item OUTPUT = Material.OAK_PLANKS.getItem(4);

    public OakPlanksRecipe() {
        this(new RecipeItem(Material.OAK_LOG));
    }

    public OakPlanksRecipe(RecipeItem... log) {
        super(OUTPUT, log);
    }

}
