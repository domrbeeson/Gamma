package domrbeeson.gamma.item.handlers;

import domrbeeson.gamma.event.events.player.PlayerRightClickBlockEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.ItemHandler;
import domrbeeson.gamma.player.Player;

public class FoodItemHandler implements ItemHandler {

    private final short health;

    public FoodItemHandler(short health) {
        this.health = health;
    }

    @Override
    public boolean use(PlayerRightClickBlockEvent event) {
        Player player = event.getPlayer();
        if (player.getHealth() >= player.getMaxHealth()) {
            return false;
        }

        player.addHealth(health);
        player.getInventory().setHeldItem(Item.AIR);
        return true;
    }

}
