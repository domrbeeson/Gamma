package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LightingBlockHandler extends BlockHandler {

    private static final Map<Byte, Byte> LIGHTING_VALUES = new HashMap<>() {{
        put(Material.TORCH.blockId, (byte) 15);
        put(Material.FIRE.blockId, (byte) 15);
        put(Material.GLOWSTONE.blockId, (byte) 15);
        put(Material.REDSTONE_ORE_GLOWING.blockId, (byte) 4);

    }};

    @Override
    public void onPlace(MinecraftServer server, Block block, Player player) {
        byte lightValue = LIGHTING_VALUES.getOrDefault(block.id(), (byte) 0);
        // TODO update light values
    }

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        byte lightValue = LIGHTING_VALUES.getOrDefault(id, (byte) 0);
        // TODO update light values
        return EMPTY_DROPS;
    }

}
