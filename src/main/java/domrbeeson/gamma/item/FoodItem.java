package domrbeeson.gamma.item;

import domrbeeson.gamma.inventory.PlayerInventory;
import domrbeeson.gamma.player.Player;

public interface FoodItem {

    int getHealth();

    default Material getItemAfterUse() {
        return Material.AIR;
    }

    default void use(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.setSlot(inv.getActiveSlot(), getItemAfterUse());
        // TODO heal player
    }

}
