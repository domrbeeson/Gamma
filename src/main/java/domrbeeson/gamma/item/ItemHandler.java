package domrbeeson.gamma.item;

import domrbeeson.gamma.event.events.player.PlayerRightClickBlockEvent;

public interface ItemHandler {

    boolean use(PlayerRightClickBlockEvent event);

    default boolean isFuel() {
        return false;
    }

    default short getFuelTicks() {
        return 0;
    }

    default boolean isSmeltable() {
        return false;
    }

    default Material getSmeltingOutput() {
        return Material.AIR;
    }
}
