package domrbeeson.gamma.crafting;

import domrbeeson.gamma.item.Material;

public class RecipeItem {

    private final short id, metadata;
    private final byte amount;
    private final Material whenUsed; // This is used for setting milk buckets to normal buckets in a cake recipe

    public RecipeItem(Material material) {
        this(material.id, material.metadata, (byte) 1, Material.AIR);
    }

    public RecipeItem(Material material, Material whenUsed) {
        this(material.id, material.metadata, (byte) 1, whenUsed);
    }

    public RecipeItem(Material material, int amount) {
        this(material.id, material.metadata, (byte) amount, Material.AIR);
    }

    public RecipeItem(short id) {
        this(id, (short) -1, (byte) 1, Material.AIR);
    }

    public RecipeItem(short id, short metadata) {
        this(id, metadata, (byte) 1, Material.AIR);
    }

    public RecipeItem(short id, short metadata, byte amount, Material whenUsed) {
        this.id = id;
        this.metadata = metadata;
        this.amount = amount;
        this.whenUsed = whenUsed;
    }

    public short getId() {
        return id;
    }

    public short getMetadata() {
        return metadata;
    }

    public byte getAmount() {
        return amount;
    }

    public boolean ignoreMetadata() {
        return metadata < 0;
    }

    public Material getWhenUsed() {
        return whenUsed;
    }

}
