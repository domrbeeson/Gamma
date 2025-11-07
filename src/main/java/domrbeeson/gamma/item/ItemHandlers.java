package domrbeeson.gamma.item;

import domrbeeson.gamma.event.events.player.PlayerRightClickBlockEvent;
import domrbeeson.gamma.item.handlers.FluidBucketItemHandler;
import domrbeeson.gamma.item.handlers.ItemHandler;

public class ItemHandlers {

    public static class EmptyItemHandler implements ItemHandler {
        @Override
        public void use(PlayerRightClickBlockEvent event) {

        }
    }

    private static final EmptyItemHandler EMPTY_ITEM_HANDLER = new EmptyItemHandler();
    private static final ItemHandler[] HANDLERS = new ItemHandler[2257];

    static {
        register(Material.WATER_BUCKET, new FluidBucketItemHandler(Material.WATER_FLOWING));
        register(Material.LAVA_BUCKET, new FluidBucketItemHandler(Material.LAVA_FLOWING));
    }

    public static void register(Material material, ItemHandler handler) {
        HANDLERS[material.id] = handler;
    }

    public static void unregister(short id) {
        HANDLERS[id] = null;
    }

    public static ItemHandler getItemHandler(short id) {
        if (id >= HANDLERS.length || id < 0) {
            return EMPTY_ITEM_HANDLER;
        }
        if (HANDLERS[id] == null) {
            return EMPTY_ITEM_HANDLER;
        }
        return HANDLERS[id];
    }

}
