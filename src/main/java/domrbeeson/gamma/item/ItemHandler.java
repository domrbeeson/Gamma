package domrbeeson.gamma.item;

import domrbeeson.gamma.event.events.player.PlayerRightClickBlockEvent;

public interface ItemHandler {

    default boolean use(Item item) {
        return false;
    }

    default boolean use(PlayerRightClickBlockEvent event) {
        return false;
    }

}
