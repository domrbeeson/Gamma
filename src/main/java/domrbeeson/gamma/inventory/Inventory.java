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

    // TODO store IDs so plugins can check what inventory the player has open
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
        outputSlots = new boolean[items.length];
    }

    protected short mapInventorySlotToClientSlot(Player player, short mappedSlot) {
        return mappedSlot;
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

        if (item == null) {
            item = Item.AIR;
        }

        short oldId = items[slot] != null ? items[slot].id() : 0;
        if (oldId == 0 && !item.isAir()) {
            slotsPopulated++;
        } else if (oldId > 0 && item.isAir()) {
            slotsPopulated--;
        }

        items[slot] = item;
        updates.add((short) slot);
        save = true;

        return true;
    }

    public Item getSlot(int slot) {
        if (!isSlotValid(slot)) {
            return Item.AIR;
        }
        if (items[slot] == null) {
            return Item.AIR;
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
        return addItem(item.id(), item.metadata(), item.amount());
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
            Item slotItem = items[slot] == null ? Item.AIR : items[slot];
            if (slotItem.id() == id && slotItem.metadata() == metadata) {
                int currentAmount = slotItem.amount();
                int addAmount = Math.min(amount, maxStack - currentAmount);
                setSlot(slot, new Item(id, metadata, currentAmount + addAmount));
                amount -= addAmount;
                if (amount == 0) {
                    break;
                }
            }
        }

        // If there's any remaining items to add, replace empty inventory slots
        if (amount > 0) {
            for (short slot = 0; slot <= items.length - 1; slot++) {
                Item slotItem = items[slot] == null ? Item.AIR : items[slot];
                if (slotItem.isAir() || (slotItem.id() == id && slotItem.metadata() == metadata)) {
                    int currentAmount = slotItem.amount();
                    int addAmount = Math.min(amount, maxStack - currentAmount);
                    setSlot(slot, new Item(id, metadata, currentAmount + addAmount));
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
            setSlot(i, Item.AIR);
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
        playerClicks.put(slot, new PlayerWindowClickEvent(player, this, (short) slot, button));
    }

    protected void onClick(PlayerWindowClickEvent event) {
        Player player = event.getPlayer();
        player.getServer().call(event);
        if (event.isCancelled()) {
            return;
        }

        short slot = event.getSlot();
        Item cursorItem = player.getCursorItem();
        Item slotItem = getSlot(slot);
        switch (event.getButton()) {
            case LEFT -> {
                if (cursorItem.getMaterial() != Material.AIR && !canDepositIntoSlot(slot, cursorItem)) {
                    return;
                }

                if (cursorItem.id() == slotItem.id() && cursorItem.metadata() == slotItem.metadata()) {
                    int depositAmount = slotItem.amount() + cursorItem.amount();
                    int maxStack = Material.get(slotItem.id(), slotItem.metadata()).maxStack;
                    int remainder = depositAmount - maxStack;
                    if (remainder < 0) {
                        remainder = 0;
                    }
                    player.setCursorItem(cursorItem.setAmount(remainder));
                    setSlot(slot, slotItem.setAmount(depositAmount));

                } else {
                    swapSlotAndCursor(slot, player);
                }
            }
            case RIGHT -> {
                if (cursorItem.isAir()) {
                    if (slotItem.amount() > 0) {
                        int pickupAmount = (int) Math.ceil(slotItem.amount() / 2d); // TODO integer divide rounds down anyway
                        player.setCursorItem(slotItem.setAmount(pickupAmount));
                        setSlot(slot, slotItem.setAmount(slotItem.amount() - pickupAmount));
                    }
                } else {
                    if (slotItem.isAir() || (slotItem.id() == cursorItem.id() && slotItem.metadata() == cursorItem.metadata())) {
                        if (slotItem.amount() < Material.get(slotItem.id(), slotItem.metadata()).maxStack) {
                            setSlot(slot, new Item(cursorItem.id(), cursorItem.metadata(), slotItem.amount() + 1));
                            player.setCursorItem(cursorItem.addAmount(-1));
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
            updates.forEach(slot -> {
                viewers.forEach(viewer -> {
                    WindowSlotPacketOut packet = new WindowSlotPacketOut(type, mapInventorySlotToClientSlot(viewer, slot), getSlot(slot));
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
        if (!canView(player)) {
            return;
        }
        if (viewers.add(player)) {
            if (this != player.getInventory()) { // Don't open player inventory as a normal inventory
                player.sendPacket(new WindowOpenPacketOut(this));
            }
            onOpen(player);

            // TODO replace this with WindowItemsPacketOut
            for (short slot = 0; slot < items.length; slot++) {
                if (items[slot] == null || items[slot].isAir()) {
                    continue;
                }
                player.sendPacket(new WindowSlotPacketOut(type, mapInventorySlotToClientSlot(player, slot), getSlot(slot)));
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

    protected void onClose(Player player) {

    }

    public boolean canDepositIntoSlot(int slot, Item item) {
        return true;
    }

}
