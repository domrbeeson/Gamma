package domrbeeson.gamma.block;

import domrbeeson.gamma.item.Material;

public class Fluid {

    public static boolean is(Material material) {
        return is(material.blockId);
    }

    public static boolean is(byte blockId) {
        return blockId == Material.WATER_SOURCE.blockId
                || blockId == Material.WATER_FLOWING.blockId
                || blockId == Material.LAVA_SOURCE.blockId
                || blockId == Material.LAVA_FLOWING.blockId;
    }

    public static boolean isSource(Material material) {
        return isSource(material.blockId);
    }

    public static boolean isSource(byte blockId) {
        return blockId == Material.WATER_SOURCE.id || blockId == Material.LAVA_SOURCE.blockId;
    }

}
