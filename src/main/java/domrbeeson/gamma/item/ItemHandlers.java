package domrbeeson.gamma.item;

import domrbeeson.gamma.item.handlers.EmptyBucketItemHandler;
import domrbeeson.gamma.item.handlers.FlintAndSteelItemHandler;
import domrbeeson.gamma.item.handlers.FluidBucketItemHandler;

public class ItemHandlers {

    private static final ItemHandler EMPTY_ITEM_HANDLER = new ItemHandler() {};
    private static final ItemHandler[] HANDLERS;

    static {
        int maxItemId = 0;
        for (Material material : Material.values()) {
            if (material.id > maxItemId) {
                maxItemId = material.id;
            }
        }
        HANDLERS = new ItemHandler[maxItemId + 1];

        register(Material.BUCKET, new EmptyBucketItemHandler());
        register(Material.WATER_BUCKET, new FluidBucketItemHandler(Material.WATER_SOURCE, false));
        register(Material.LAVA_BUCKET, new FluidBucketItemHandler(Material.LAVA_SOURCE, true));
        register(Material.FLINT_AND_STEEL, new FlintAndSteelItemHandler());

        for (int i = 0; i < HANDLERS.length; i++) {
            if (HANDLERS[i] == null) {
                HANDLERS[i] = EMPTY_ITEM_HANDLER;
            }
        }
    }

    public static void register(short id, ItemHandler handler) {
        HANDLERS[id] = handler;
    }

    public static void register(Material material, ItemHandler handler) {
        register(material.id, handler);
    }

    public static void unregister(short id) {
        HANDLERS[id] = null;
    }

    public static ItemHandler getItemHandler(short id) {
        if (id >= HANDLERS.length || id < 0) {
            return EMPTY_ITEM_HANDLER;
        }
        return HANDLERS[id];
    }

    public static ItemHandler getItemHandler(Material material) {
        return getItemHandler(material.id);
    }

}
