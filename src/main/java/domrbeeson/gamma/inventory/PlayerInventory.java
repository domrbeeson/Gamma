package domrbeeson.gamma.inventory;

import domrbeeson.gamma.crafting.RecipeManager;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.player.Player;

import java.util.concurrent.CompletableFuture;

public class PlayerInventory extends CraftingInventory {

    public static final int HOTBAR_SLOTS = 9;
    public static final int INVENTORY_SLOTS = 27;
    public static final int CRAFTING_SLOTS = 4;

    private static final short[] MAPPINGS = new short[] {
            // Crafting output
            44,

            // Crafting grid
            40, 41,
            42, 43,

            // Armour
            36, 37, 38, 39,

            // Inventory
            9, 10, 11, 12, 13, 14, 15, 16, 17, // Raw slot 17 to 9
            18, 19, 20, 21, 22, 23, 24, 25, 26, // Raw slot 26 to 18
            27, 28, 29, 30, 31, 32, 33, 34, 35, // Raw slot 27 to 35

            // Hotbar
            0, 1, 2, 3, 4, 5, 6, 7, 8 // Raw slot 36 to 44
    };
    private static final short[] OUTPUT_MAPPINGS = reversePlayerInventoryMappings(MAPPINGS);
    private static final int[][] CRAFTING_GRID = new int[][] {
            { 40, 41 },
            { 42, 43 }
    };

    private final String username;

    private int activeSlot = 0;
    private int activeSlotNextTick = 0;

    public PlayerInventory(String username, RecipeManager recipeManager) {
        this(username, recipeManager, new Item[0]);
    }

    public PlayerInventory(String username, RecipeManager recipeManager, Item[] items) {
        super(recipeManager, CRAFTING_GRID, 44, items);
        this.username = username;

    }

    public void setCraftingSlot(int slot, Item item) {
        if (slot < 0 || slot >= CRAFTING_SLOTS) {
            return;
        }
        setSlot(slot + 40, item);
        // TODO invoke some crafting util to get crafting output
    }

    public void setCraftingOutput(Item item) {
        super.setSlot(0, item);
    }

    public void setHelmet(Item item) {
        setSlot(36, item);
    }

    public void setChest(Item item) {
        setSlot(37, item);
    }

    public void setLegs(Item item) {
        setSlot(38, item);
    }

    public void setFeet(Item item) {
        setSlot(39, item);
    }

    public void setInventorySlot(int slot, Item item) {
        if (slot < 0 || slot >= INVENTORY_SLOTS) {
            return;
        }
        setSlot(slot + 9, item);
    }

    public void setHotbarSlot(int slot, Item item) {
        if (slot < 0 || slot >= HOTBAR_SLOTS) {
            return;
        }
        setSlot(slot, item);
    }

    public Item getHotbarSlot(int slot) {
        if (slot < 0 || slot >= HOTBAR_SLOTS) {
            return Item.AIR;
        }
        return getSlot(slot);
    }

    public void setActiveSlot(int activeSlot) {
        if (activeSlot < 0 || activeSlot > 8) {
            return;
        }
        activeSlotNextTick = activeSlot;
    }

    public int getActiveSlot() {
        return activeSlot;
    }

    public void setHeldItem(Item item) {
        setHotbarSlot(activeSlot, item);
    }

    public Item getHeldItem() {
        return getHotbarSlot(activeSlot);
    }

    @Override
    protected short mapInventorySlotToClientSlot(short invSlot) {
        return OUTPUT_MAPPINGS[invSlot];
    }

    @Override
    public void tick(long ticks) {
        activeSlot = activeSlotNextTick;
        super.tick(ticks);
    }

    @Override
    public CompletableFuture<Void> addViewer(Player player) {
        if (!player.getUsername().equalsIgnoreCase(username)) {
            throw new UnsupportedOperationException("Player '" + player.getUsername() + "' cannot view the inventory of '" + username + "'.");
        }
        return super.addViewer(player);
    }

    public short mapRawSlot(int rawSlot) {
        return MAPPINGS[rawSlot];
    }

}
