package domrbeeson.gamma.inventory;

import domrbeeson.gamma.crafting.RecipeManager;
import domrbeeson.gamma.crafting.RecipeState;
import domrbeeson.gamma.event.events.PlayerWindowClickEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import org.jetbrains.annotations.Nullable;

public class CraftingInventory extends Inventory {

    private static final int[][] CRAFTING_TABLE_GRID = new int[][] {
        { 1, 2, 3 },
        { 4, 5, 6 },
        { 7, 8, 9 }
    };

    private final RecipeManager recipeManager;
    private final int[][] craftingGrid;
    private final int outputSlot;

    private @Nullable RecipeState recipeState = null;
    private int craftingSlotsPopulated = 0;

    public CraftingInventory(RecipeManager recipeManager) {
        this(recipeManager, CRAFTING_TABLE_GRID);
    }

    public CraftingInventory(RecipeManager recipeManager, int[][] craftingGrid) {
        super(InventoryType.CRAFTING_TABLE, "");
        this.recipeManager = recipeManager;
        this.craftingGrid = craftingGrid;
        outputSlot = 0;
    }

    protected CraftingInventory(RecipeManager recipeManager, int[][] craftingGrid, int outputSlot) {
        super(InventoryType.PLAYER, "");
        this.recipeManager = recipeManager;
        this.craftingGrid = craftingGrid;
        this.outputSlot = outputSlot;
    }

    public void setOutput(Item item) {
        if (item == null) {
            item = Item.AIR;
        }
        setSlot(outputSlot, item);
    }

    public Item getOutput() {
        return getSlot(outputSlot);
    }

    @Override
    public boolean setSlot(int slot, @Nullable Item item, boolean update) {
        boolean inCraftingGrid = slot >= craftingGrid[0][0] && slot <= craftingGrid[craftingGrid.length - 1][craftingGrid.length - 1];
        int beforeId = 0;
        if (inCraftingGrid) {
            beforeId = getSlot(slot).id();
        }
        boolean updated = super.setSlot(slot, item, update);
        if (inCraftingGrid) {
            if (beforeId == 0 && (item != null && item.id() > 0)) {
                craftingSlotsPopulated++;
            } else if (beforeId > 0 && (item == null || item.id() == 0)) {
                craftingSlotsPopulated--;
            }
            updateCraftingGrid();
        }
        return updated;
    }

    @Override
    protected void onClick(PlayerWindowClickEvent event) {
        if (event.getSlot() != outputSlot) {
            super.onClick(event);
            return;
        }
        if (recipeState == null) {
            return;
        }
        Item output = getOutput();
        if (output != null && output.id() != 0) {
            Player player = event.getPlayer();
            Item cursor = player.getCursorItem();
            if (cursor.id() > 0) {
                Material cursorMaterial = Material.get(cursor.id(), cursor.metadata());
                if (cursor.id() != output.id() || cursor.metadata() != output.metadata()) {
                    return;
                }
                int oldAmount = cursor.amount();
                int newAmount = Math.min(cursor.amount() + output.amount(), cursorMaterial.maxStack);
                if (oldAmount == newAmount) {
                    return;
                }
                int remainder = cursor.amount() + output.amount() - newAmount;
                player.setCursorItem(cursorMaterial.getItem(newAmount));
                setOutput(cursorMaterial.getItem(remainder));
            } else {
                player.setCursorItem(output);
            }
            Item[][] populatedSlots = recipeState.populatedSlots();
            for (int x = 0; x < populatedSlots.length; x++) {
                for (int y = 0; y < populatedSlots[x].length; y++) {
                    if (populatedSlots[x][y] == null) {
                        continue;
                    }
                    setSlot(craftingGrid[x][y], Material.get(populatedSlots[x][y].id(), populatedSlots[x][y].metadata()).getItem(populatedSlots[x][y].amount() - 1));
                }
            }
        }
    }

    @Override
    public void onClose(Player player) {
        if (!hasViewers() && craftingSlotsPopulated > 0) {
            Item item;
            for (int x = 0; x < craftingGrid.length; x++) {
                for (int y = 0; y < craftingGrid[x].length; y++) {
                    item = getSlot(craftingGrid[x][y]);
                    if (item.id() == 0) {
                        continue;
                    }
                    // TODO drop crafting inventory item on the ground
                    System.out.println("TODO drop " + item.amount() + "x " + item.id() + ":" + item.metadata() + " from crafting inventory");
                    setSlot(craftingGrid[x][y], Item.AIR);
                }
            }
        }
    }

    private void updateCraftingGrid() {
        Item[][] grid = new Item[craftingGrid.length][craftingGrid[0].length];
        for (int x = 0; x < craftingGrid.length; x++) {
            for (int y = 0; y < craftingGrid[x].length; y++) {
                grid[x][y] = getSlot(craftingGrid[x][y]);
            }
        }
        recipeState = recipeManager.checkRecipe(grid);
        if (recipeState != null) {
            setOutput(recipeState.recipe().getOutput());
        } else {
            setOutput(null);
        }
    }

}
