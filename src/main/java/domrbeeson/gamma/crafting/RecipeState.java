package domrbeeson.gamma.crafting;

import domrbeeson.gamma.item.Item;

public record RecipeState(
        CraftingRecipe recipe,
        Item[][] populatedSlots // TODO need to know how much to remove from each slot
) {
}
