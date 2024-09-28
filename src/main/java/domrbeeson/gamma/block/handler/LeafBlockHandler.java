package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.WeightedDropTable;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

import java.util.ArrayList;
import java.util.List;

public class LeafBlockHandler extends BlockHandler {

    private final WeightedDropTable oakDropTable = new WeightedDropTable().add(Material.OAK_SAPLING, 5).add(Material.APPLE, 0.5).nothing(94.5);
    private final WeightedDropTable birchDropTable = new WeightedDropTable().add(Material.BIRCH_SAPLING, 5).add(Material.APPLE, 0.5).nothing(94.5);
    private final WeightedDropTable spruceDropTable = new WeightedDropTable().add(Material.SPRUCE_SAPLING, 5).add(Material.APPLE, 0.5).nothing(94.5);

    @Override
    public List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, short toolId) {
        List<Item> drops;
        switch (metadata) {
            case 0 -> drops = getDrop(oakDropTable);
            case 1 -> drops = getDrop(birchDropTable);
            case 2 -> drops = getDrop(spruceDropTable);
            default -> drops = new ArrayList<>();
        }
        return drops;
    }

    private List<Item> getDrop(WeightedDropTable dropTable) {
        Material material = dropTable.next();
        if (material == null) {
            return EMPTY_DROPS;
        }
        return List.of(material.getItem());
    }

    @Override
    public void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long tick) {
        // TODO check whether to decay
    }

}
