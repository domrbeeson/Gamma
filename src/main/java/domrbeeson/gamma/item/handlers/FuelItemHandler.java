package domrbeeson.gamma.item.handlers;

import domrbeeson.gamma.event.events.player.PlayerRightClickBlockEvent;
import domrbeeson.gamma.item.ItemHandler;

public class FuelItemHandler implements ItemHandler {

    private final short fuelTicks;

    public FuelItemHandler(int fuelTicks) {
        this.fuelTicks = (short) fuelTicks;
    }

    @Override
    public boolean use(PlayerRightClickBlockEvent event) {
        return false;
    }

    @Override
    public boolean isFuel() {
        return true;
    }

    @Override
    public short getFuelTicks() {
        return fuelTicks;
    }
}
