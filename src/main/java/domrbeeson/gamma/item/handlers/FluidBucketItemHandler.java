package domrbeeson.gamma.item.handlers;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.event.events.player.PlayerRightClickBlockEvent;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Dimension;

public class FluidBucketItemHandler implements ItemHandler {

    private final Material fluid;
    private final boolean allowedInNether;

    public FluidBucketItemHandler(Material fluid, boolean allowedInNether) {
        this.fluid = fluid;
        this.allowedInNether = allowedInNether;
    }

    @Override
    public void use(PlayerRightClickBlockEvent event) {
        event.getPlayer().getInventory().setHeldItem(Material.BUCKET.getItem());

        if (!allowedInNether && event.getPlayer().getWorld().getFormat().getDimension() == Dimension.NETHER) {
            return;
        }

        Pos blockPos = event.getDirection().applyDirection(event.getX(), event.getY(), event.getZ());
        event.getPlayer().getWorld().setBlock(blockPos.getBlockX(), blockPos.getBlockY(), blockPos.getBlockZ(), fluid);
    }
}
