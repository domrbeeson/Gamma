package domrbeeson.gamma.inventory;

import domrbeeson.gamma.Tickable;
import domrbeeson.gamma.Viewable;
import domrbeeson.gamma.event.events.PlayerWindowClickEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.network.MouseButton;
import domrbeeson.gamma.network.packet.out.WindowClosePacketOut;
import domrbeeson.gamma.network.packet.out.WindowOpenPacketOut;
import domrbeeson.gamma.network.packet.out.WindowSlotPacketOut;
import domrbeeson.gamma.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;

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
    private boolean changed = false;

    public Inventory(InventoryType type, String title) {
        this(type, title, new Item[0]);
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
        return setSlot(slot, material.getItem());
    }

    public final boolean setSlot(int slot, @Nullable Item item) {
        return setSlot(slot, item, true);
    }

    public boolean setSlot(int slot, @Nullable Item item, boolean update) {
        if (!isSlotValid(slot)) {
            return false;
        }
        Item slotItem = getSlot(slot);
        if (item == null || item.id() == 0 || item.amount() == 0) {
            item = Item.AIR;
        } else if (slotItem.equals(item)) {
            return false;
        }
        if ((items[slot] == null || items[slot].id() == 0) && item.id() > 0) {
            slotsPopulated++;
        } else if ((items[slot] != null && items[slot].id() > 0) && item.id() == 0) {
            slotsPopulated--;
        }
        items[slot] = item;
        if (update) {
            updates.add((short) slot);
        }
        changed = true;
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
        return addItem(item.id(), item.metadata(), item.amount(), items.length - 1);
    }

    public int addItem(Material material, int amount) {
        return addItem(material.id, material.metadata, amount, items.length - 1);
    }

    // Returns amount of items that weren't able to be added
    protected int addItem(short id, short metadata, int amount, int finishIndex) {
        if (amount == 0) {
            return 0;
        }
        short maxStack = Material.get(id, metadata).maxStack;
        Item slotItem;
        short slotId, slotMetadata;
        byte slotAmount;
        for (int slot = 0; slot <= finishIndex; slot++) {
            slotItem = getSlot(slot);
            slotId = slotItem.id();
            slotMetadata = slotItem.metadata();
            slotAmount = slotId == 0 ? 0 : slotItem.amount();
            if (slotId == id && slotMetadata == metadata && slotAmount < maxStack) {
                byte addAmount = (byte) Math.min(maxStack - slotAmount, amount);
                setSlot(slot, Material.get(id, metadata).getItem(slotAmount + addAmount));
                amount -= addAmount;
                if (amount == 0) {
                    return 0;
                }
            }
        }
        for (int slot = 0; slot <= finishIndex; slot++) {
            slotItem = getSlot(slot);
            slotId = slotItem.id();
            slotAmount = slotId == 0 ? 0 : slotItem.amount();
            if (slotId == id || slotId == 0) {
                byte addAmount = (byte) Math.min(maxStack - slotAmount, amount);
                setSlot(slot, Material.get(id, metadata).getItem(slotAmount + addAmount));
                amount -= addAmount;
                if (amount == 0) {
                    return 0;
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
                if (cursorItem.id() == slotItem.id() && cursorItem.metadata() == slotItem.metadata()) {
                    int depositAmount = slotItem.amount() + cursorItem.amount();
                    int maxStack = Material.get(slotItem.id(), slotItem.metadata()).maxStack;
                    int remainder = depositAmount - maxStack;
                    if (remainder < 0) {
                        remainder = 0;
                    }
                    player.setCursorItem(Material.get(cursorItem.id(), cursorItem.metadata()).getItem(remainder));
                    setSlot(slot, Material.get(slotItem.id(), slotItem.metadata()).getItem(depositAmount));
                } else {
                    swapSlotAndCursor(slot, player);
                }
            }
            case RIGHT -> {
                if (cursorItem.id() == 0) {
                    if (slotItem.amount() > 0) {
                        int pickupAmount = (int) Math.ceil(slotItem.amount() / 2d);
                        player.setCursorItem(Material.get(slotItem.id(), slotItem.metadata()).getItem(pickupAmount));
                        setSlot(slot, Material.get(slotItem.id(), slotItem.metadata()).getItem(slotItem.amount() - pickupAmount));
                    }
                } else {
                    if (slotItem.id() == 0 || (slotItem.id() == cursorItem.id() && slotItem.metadata() == cursorItem.metadata())) {
                        if (slotItem.amount() < Material.get(slotItem.id(), slotItem.metadata()).maxStack) {
                            setSlot(slot, Material.get(cursorItem.id(), cursorItem.metadata()).getItem(slotItem.amount() + 1));
                            player.setCursorItem(Material.get(cursorItem.id(), cursorItem.metadata()).getItem(cursorItem.amount() - 1));
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
        Map<Integer, PlayerWindowClickEvent> events;
        synchronized (playerClicks) {
            events = new HashMap<>(playerClicks);
            playerClicks.clear();
        }
        events.values().forEach(this::onClick);

        if (!updates.isEmpty()) {
            updates.forEach(invSlot -> {
                viewers.forEach(viewer -> {
                    WindowSlotPacketOut packet = new WindowSlotPacketOut(type, mapInventorySlotToClientSlot(invSlot), getSlot(invSlot));
                    viewer.sendPacket(packet);
                });
            });
            updates.clear();
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
    public CompletableFuture<Void> addViewer(Player player) {
        if (viewers.add(player)) {
            player.sendPacket(new WindowOpenPacketOut(this));
            onOpen(player);
            for (short slot = 0; slot < items.length; slot++) {
                if (items[slot] == null || items[slot].id() == 0) {
                    continue;
                }
                // TODO replace this with WindowItemsPacketOut
                WindowSlotPacketOut packet = new WindowSlotPacketOut(type, mapInventorySlotToClientSlot(slot), getSlot(slot));
                player.sendPacket(packet);
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> removeViewer(Player player) {
        if (viewers.remove(player)) {
            player.sendPacket(new WindowClosePacketOut(player));
            onClose(player);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public List<Player> getViewers() {
        return viewers;
    }

    @Override
    public boolean hasViewers() {
        return !viewers.isEmpty();
    }

    public @Nullable Item[] getChanges() {
        if (changed) {
            changed = false;
            return items;
        }
        return null;
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

}
