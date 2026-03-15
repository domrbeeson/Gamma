package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.event.events.block.BlockChangeEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LightingBlockHandler implements BlockHandler {

    private static final Map<Byte, Byte> LIGHTING_VALUES = new HashMap<>() {{
        put(Material.TORCH.blockId, (byte) 15);
        put(Material.FIRE.blockId, (byte) 15);
        put(Material.GLOWSTONE.blockId, (byte) 15);
        put(Material.REDSTONE_ORE_GLOWING.blockId, (byte) 4);

    }};

    @Override
    public void onPlace(MinecraftServer server, BlockChangeEvent event, @Nullable Player player) {
        byte lightValue = LIGHTING_VALUES.getOrDefault(event.getNewId(), (byte) 0);
        // TODO update light values
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        byte lightValue = LIGHTING_VALUES.getOrDefault(id, (byte) 0);
        // TODO update light values
        return List.of();
    }

}
