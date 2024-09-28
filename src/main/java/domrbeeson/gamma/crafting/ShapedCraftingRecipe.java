package domrbeeson.gamma.crafting;

import domrbeeson.gamma.item.Item;
import org.jetbrains.annotations.Nullable;

public class ShapedCraftingRecipe implements CraftingRecipe {

    private final Item output;
    private final RecipeItem[][] recipe;
    private final int filledSlots;

    public ShapedCraftingRecipe(Item output, RecipeItem[][] recipe) {
        this.output = output;
        this.recipe = recipe;

        int filledSlots = 0;
        for (RecipeItem[] xItems : recipe) {
            for (RecipeItem item : xItems) {
                if (item.getId() != 0) {
                    filledSlots++;
                }
            }
        }
        this.filledSlots = filledSlots;
    }

    @Override
    public @Nullable RecipeState matches(Item[][] craftingGrid) {
        if (craftingGrid.length < recipe.length || craftingGrid[0].length < recipe[0].length) {
            return null;
        }

        int airSlots = 0;
        int xStart = -1;
        int yStart = -1;
        for (int x = 0; x < craftingGrid.length; x++) {
            for (int y = 0; y < craftingGrid[x].length; y++) {
                if (craftingGrid[x][y].id() == 0) {
                    airSlots++;
                } else if (xStart == -1) {
                    xStart = x;
                    yStart = y;
                }
            }
        }

        if (xStart == -1 || yStart == -1) {
            return null;
        }

        int craftingGridSlots = craftingGrid.length * craftingGrid[0].length;
        Item[][] populatedSlots = new Item[craftingGrid.length][craftingGrid[0].length];

        Item gridItem;
        RecipeItem recipeItem;
        int matchedSlots = 0;
        int x, y;

        for (int recipeX = 0; recipeX < recipe.length; recipeX++) {
            for (int recipeY = 0; recipeY < recipe[recipeX].length; recipeY++) {
                x = recipeX + xStart;
                if (x >= craftingGrid.length) {
                    continue;
                }
                y = recipeY + yStart;
                if (y >= craftingGrid[xStart].length) {
                    continue;
                }

                gridItem = craftingGrid[x][y];
                recipeItem = recipe[recipeX][recipeY];
                if (recipeItem.getId() != gridItem.id() || (!recipeItem.ignoreMetadata() && recipeItem.getMetadata() != gridItem.metadata()) || (recipeItem.getId() != 0 && recipeItem.getAmount() > gridItem.amount())) {
                    return null;
                }
                if (gridItem.id() != 0) {
                    matchedSlots++;
                    populatedSlots[x][y] = gridItem;
                }
            }
        }

        if (matchedSlots == filledSlots && matchedSlots + airSlots == craftingGridSlots) {
            return new RecipeState(this, populatedSlots);
        }
        return null;
    }

    @Override
    public Item getOutput() {
        return output;
    }

}
