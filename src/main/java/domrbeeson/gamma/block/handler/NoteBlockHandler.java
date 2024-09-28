package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;

import java.util.List;

public class NoteBlockHandler extends PoweredBlockHandler {

    private static final List<Item> DROPS = List.of(Material.NOTE_BLOCK.getItem());

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        return DROPS;
    }

    @Override
    public void onLeftClick(MinecraftServer server, Block block, Player player) {
        // TODO play sound
    }

    @Override
    public boolean onRightClick(MinecraftServer server, Block block, Player player) {
        // TODO change pitch
        return true;
    }

    @Override
    public void onPower(MinecraftServer server, Block me, byte powerLevel) {
        // TODO play sound if power level > 0
    }

}
