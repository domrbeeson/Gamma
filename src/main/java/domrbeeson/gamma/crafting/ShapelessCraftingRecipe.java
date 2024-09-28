package domrbeeson.gamma.crafting;

import domrbeeson.gamma.item.Item;

public class ShapelessCraftingRecipe implements CraftingRecipe {

    private final Item output;
    private final RecipeItem[] recipe;

    public ShapelessCraftingRecipe(Item output, RecipeItem... recipe) {
        this.output = output;
        this.recipe = recipe;
    }

    @Override
    public RecipeState matches(Item[][] craftingGrid) {
        int craftingGridSlots = craftingGrid.length * craftingGrid[0].length;

        Item[][] populatedSlots = new Item[craftingGrid.length][craftingGrid[0].length];

        Item item;
        int matches = 0;
        int air = 0;
        for (int x = 0; x < craftingGrid.length; x++) {
            for (int y = 0; y < craftingGrid[x].length; y++) {
                item = craftingGrid[x][y];
                if (item == null || item.id() == 0) {
                    air++;
                    continue;
                }
                if (!isInRecipe(item)) {
                    return null;
                }
                matches++;
                populatedSlots[x][y] = item;
            }
        }
        if (matches == recipe.length && matches + air == craftingGridSlots) {
            return new RecipeState(this, populatedSlots);
        }
        return null;
    }

    private boolean isInRecipe(Item iam) {
        for (RecipeItem ri : recipe) {
            if (ri.getId() == iam.id() && (!ri.ignoreMetadata() && ri.getMetadata() == iam.metadata())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Item getOutput() {
        return output;
    }

}
