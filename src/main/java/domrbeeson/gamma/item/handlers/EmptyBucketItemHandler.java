package domrbeeson.gamma.item.handlers;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.event.events.player.PlayerRightClickBlockEvent;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.HashMap;
import java.util.Map;

public class EmptyBucketItemHandler implements ItemHandler {

    private static final Map<Material, Material> FLUID_TO_BUCKET = new HashMap<>() {{
        put(Material.WATER_SOURCE, Material.WATER_BUCKET);
        put(Material.LAVA_SOURCE, Material.LAVA_BUCKET);
    }};

    @Override
    public void use(PlayerRightClickBlockEvent event) {
        Pos fluidPos = event.getDirection().applyDirection(event.getX(), event.getY(), event.getZ());
        Chunk chunk = event.getPlayer().getWorld().getChunk(fluidPos);

        Material fluid = chunk.getMaterial(fluidPos.getBlockX(), fluidPos.getBlockY(), fluidPos.getBlockZ());
        Material bucket = FLUID_TO_BUCKET.get(fluid);
        if (bucket == null) {
            return;
        }

        chunk.setBlock(fluidPos.getBlockX(), fluidPos.getBlockY(), fluidPos.getBlockZ(), Material.AIR);
        event.getPlayer().getInventory().setHeldItem(bucket.getItem());
    }
}
