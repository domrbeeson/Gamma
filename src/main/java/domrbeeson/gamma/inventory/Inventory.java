package domrbeeson.gamma.inventory;

import domrbeeson.gamma.Tickable;
import domrbeeson.gamma.Viewable;
import domrbeeson.gamma.event.events.player.PlayerWindowClickEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.network.MouseButton;
import domrbeeson.gamma.network.packet.out.WindowClosePacketOut;
import domrbeeson.gamma.network.packet.out.WindowOpenPacketOut;
import domrbeeson.gamma.network.packet.out.WindowSlotPacketOut;
import domrbeeson.gamma.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class Inventory implements Tickable, Viewable {

    public static final short[] PLAYER_INVENTORY_MAPPINGS = new short[] {
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            0, 1, 2, 3, 4, 5, 6, 7, 8
    };

    private final Item[] items;
    private final List<Player> viewers = new ArrayList<>();
    private final InventoryType type;
    private final String title;
    private final Set<Short> updates = new HashSet<>();
    private final boolean[] outputSlots;
    private final Map<Integer, PlayerWindowClickEvent> playerClicks = new HashMap<>();

    private short slotsPopulated = 0;
    private boolean save = false;

    public Inventory(InventoryType type, String title) {
        this(type, title, new Item[type.slots]);
    }

    public Inventory(InventoryType type, String title, Item[] items) {
        this.type = type;
        this.title = title;
        this.items = items;
        outputSlots = new boolean[type.slots];
    }

    private static short[] generateDefaultMappings(InventoryType type) {
        short[] mappings = new short[type.slots];
        for (short i = 0; i < mappings.length; i++) {
            mappings[i] = i;
        }
        return mappings;
    }

    protected short mapInventorySlotToClientSlot(short invSlot) {
        return invSlot;
    }

    public InventoryType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public final boolean setSlot(int slot, Material material) {
        return setSlot(slot, new Item(material));
    }

    public final boolean setSlot(int slot, @Nullable Item item) {
        return setSlot(slot, item, true);
    }

    public boolean setSlot(int slot, @Nullable Item item, boolean update) {
        if (!isSlotValid(slot)) {
            return false;
        }
        Item slotItem = getSlot(slot);
        if (item == null || item.getId() == 0 || item.getAmount() == 0) {
            item = Item.getAir();
        } else if (slotItem.equals(item)) {
            return false;
        }
        if ((items[slot] == null || items[slot].getId() == 0) && item.getId() > 0) {
            slotsPopulated++;
        } else if ((items[slot] != null && items[slot].getId() > 0) && item.getId() == 0) {
            slotsPopulated--;
        }
        items[slot] = item;
        if (update) {
            updates.add((short) slot);
        }
        save = true;
        return true;
    }

    public Item getSlot(int slot) {
        if (!isSlotValid(slot)) {
            return Item.getAir();
        }
        if (items[slot] == null) {
            return Item.getAir();
        }
        return items[slot];
    }

    public short getSlotsPopulated() {
        return slotsPopulated;
    }

    public Item[] getSlots() {
        return items;
    }

    public int addItem(Item item) {
        return addItem(item.getId(), item.getMetadata(), item.getAmount());
    }

    /**
        @return amount of items that were unable to be added
     */
    public int addItem(short id, short metadata, int amount) {
        if (amount == 0) {
            return 0;
        }
        if (id == 0) {
            return amount;
        }

        // Find existing stacks to merge with first
        short maxStack = Material.get(id, metadata).maxStack;
        for (short slot = 0; slot <= items.length - 1; slot++) {
            Item slotItem = items[slot] == null ? Item.getAir() : items[slot];
            if (slotItem.getId() == id && slotItem.getMetadata() == metadata) {
                slotItem.setIdAndMetadata(id, metadata);
                int currentAmount = slotItem.getAmount();
                int addAmount = Math.min(amount, maxStack - currentAmount);
                slotItem.addAmount(addAmount);
                updates.add(slot);
                amount -= addAmount;
                if (amount == 0) {
                    break;
                }
            }
        }

        // If there's any remaining items to add, replace empty inventory slots
        if (amount > 0) {
            for (short slot = 0; slot <= items.length - 1; slot++) {
                Item slotItem = items[slot] == null ? Item.getAir() : items[slot];
                if (slotItem.getId() == 0 || (slotItem.getId() == id && slotItem.getMetadata() == metadata)) {
                    slotItem.setIdAndMetadata(id, metadata);
                    int currentAmount = slotItem.getAmount();
                    int addAmount = Math.min(amount, maxStack - currentAmount);
                    slotItem.addAmount(addAmount);
                    updates.add(slot);
                    amount -= addAmount;
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
        return amount;
    }

    public void clear() {
        for (short i = 0; i < items.length; i++) {
            setSlot(i, Item.getAir());
        }
    }

    protected void setSlotOutput(int slot, boolean restricted) {
        if (!isSlotValid(slot)) {
            return;
        }
        outputSlots[slot] = restricted;
    }

    public boolean isOutputSlot(int slot) {
        if (!isSlotValid(slot)) {
            return true;
        }
        return outputSlots[slot];
    }

    protected final boolean isSlotValid(int slot) {
        return slot >= 0 && slot < items.length;
    }

    public final void click(Player player, int slot, MouseButton button) {
        if (slot < 0 || slot >= items.length) {
            return;
        }
        // Only register a click from the first player that clicked the slot
        if (playerClicks.containsKey(slot)) {
            return;
        }
        playerClicks.put(slot, new PlayerWindowClickEvent(player, this, slot, button));
    }

    protected void onClick(PlayerWindowClickEvent event) {
        Player player = event.getPlayer();
        player.getServer().call(event);
        if (event.isCancelled()) {
            return;
        }

        int slot = event.getSlot();
        Item cursorItem = player.getCursorItem();
        Item slotItem = getSlot(slot);
        switch (event.getButton()) {
            case LEFT -> {
                if (cursorItem.getMaterial() != Material.AIR && !canDepositIntoSlot(slot, cursorItem)) {
                    return;
                }

                if (cursorItem.getId() == slotItem.getId() && cursorItem.getMetadata() == slotItem.getMetadata()) {
                    int depositAmount = slotItem.getAmount() + cursorItem.getAmount();
                    int maxStack = Material.get(slotItem.getId(), slotItem.getMetadata()).maxStack;
                    int remainder = depositAmount - maxStack;
                    if (remainder < 0) {
                        remainder = 0;
                    }
                    cursorItem.setAmount(remainder);
//                    player.setCursorItem(Material.get(cursorItem.getId(), cursorItem.getMetadata()).getItem(remainder));
                    setSlot(slot, new Item(slotItem.getId(), slotItem.getMetadata(), depositAmount));
                } else {
                    swapSlotAndCursor(slot, player);
                }
            }
            case RIGHT -> {
                if (cursorItem.getId() == 0) {
                    if (slotItem.getAmount() > 0) {
                        int pickupAmount = (int) Math.ceil(slotItem.getAmount() / 2d);
                        cursorItem.setAmount(pickupAmount);
//                        player.setCursorItem(Material.get(slotItem.getId(), slotItem.getMetadata()).getItem(pickupAmount));
                        slotItem.setAmount(slotItem.getAmount() - pickupAmount);
//                        setSlot(slot, Material.get(slotItem.getId(), slotItem.getMetadata()).getItem(slotItem.getAmount() - pickupAmount));
                    }
                } else {
                    if (slotItem.getId() == 0 || (slotItem.getId() == cursorItem.getId() && slotItem.getMetadata() == cursorItem.getMetadata())) {
                        if (slotItem.getAmount() < Material.get(slotItem.getId(), slotItem.getMetadata()).maxStack) {
                            slotItem.setIdAndMetadata(cursorItem.getId(), cursorItem.getMetadata());
                            slotItem.setAmount(slotItem.getAmount() + 1);
//                            setSlot(slot, Material.get(cursorItem.getId(), cursorItem.getMetadata()).getItem(slotItem.getAmount() + 1));
                            cursorItem.setAmount(cursorItem.getAmount() - 1);
//                            player.setCursorItem(Material.get(cursorItem.getId(), cursorItem.getMetadata()).getItem(cursorItem.getAmount() - 1));
                        }
                    } else {
                        swapSlotAndCursor(slot, player);
                    }
                }
            }
        }
    }

    @Override
    public void tick(long ticks) {
        playerClicks.values().forEach(this::onClick);
        playerClicks.clear();

        if (!updates.isEmpty()) {
            updates.forEach(invSlot -> {
                viewers.forEach(viewer -> {
                    WindowSlotPacketOut packet = new WindowSlotPacketOut(type, mapInventorySlotToClientSlot(invSlot), getSlot(invSlot));
                    viewer.sendPacket(packet);
                });
            });
            updates.clear();
            save = false;
        }
    }

    private void swapSlotAndCursor(int slot, Player player) {
        Item cursorItem = player.getCursorItem();
        Item slotItem = getSlot(slot);
        player.setCursorItem(slotItem);
        setSlot(slot, cursorItem);
    }

    @Override
    public boolean isViewing(Player player) {
        return viewers.contains(player);
    }

    @Override
    public void addViewer(Player player) {
        if (viewers.add(player)) {
            player.sendPacket(new WindowOpenPacketOut(this));
            onOpen(player);

            // TODO replace this with WindowItemsPacketOut
            for (short slot = 0; slot < items.length; slot++) {
                if (items[slot] == null || items[slot].getId() == 0) {
                    continue;
                }
                WindowSlotPacketOut packet = new WindowSlotPacketOut(type, mapInventorySlotToClientSlot(slot), getSlot(slot));
                player.sendPacket(packet);
            }
        }
    }

    @Override
    public void removeViewer(Player player) {
        if (viewers.remove(player)) {
            player.sendPacket(new WindowClosePacketOut(player));
            onClose(player);
        }
    }

    @Override
    public List<Player> getViewers() {
        return viewers;
    }

    @Override
    public boolean hasViewers() {
        return !viewers.isEmpty();
    }

    protected void markForSaving() {
        save = true;
    }

    public boolean shouldSave() {
        return save;
    }

    protected static short[] reversePlayerInventoryMappings(short[] playerInvMappings) {
        short[] mappings = new short[playerInvMappings.length];
        for (short i = 0; i < playerInvMappings.length; i++) {
            mappings[playerInvMappings[i]] = i;
        }
        return mappings;
    }

    public void onOpen(Player player) {

    }

    public void onClose(Player player) {

    }

    public boolean canDepositIntoSlot(int slot, Item item) {
        return true;
    }

}
