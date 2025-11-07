package domrbeeson.gamma.item.handlers;

import domrbeeson.gamma.event.events.player.PlayerRightClickBlockEvent;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Dimension;

public class FluidBucketItemHandler implements ItemHandler {

    private final Material fluid;

    public FluidBucketItemHandler(Material fluid) {
        this.fluid = fluid;
    }

    @Override
    public void use(PlayerRightClickBlockEvent event) {
        event.getPlayer().getInventory().setHeldItem(Material.BUCKET.getItem());

        if (fluid == Material.WATER_FLOWING && event.getPlayer().getWorld().getFormat().getDimension() == Dimension.NETHER) {
            return;
        }

        event.getPlayer().getWorld().setBlock(event.getX(), event.getY(), event.getZ(), fluid);
    }
}
