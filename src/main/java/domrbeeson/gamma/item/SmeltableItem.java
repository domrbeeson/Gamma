package domrbeeson.gamma.item;

public abstract class SmeltableItem extends Item {

    public SmeltableItem(Material material) {
        super(material);
    }

    public SmeltableItem(Material material, int amount) {
        this(material.id, material.metadata, (byte) amount);
    }

    public SmeltableItem(short id, short metadata) {
        this(id, metadata, (byte) 1);
    }

    public SmeltableItem(short id, short metadata, int amount) {
        super(id, metadata, amount);
    }

    public abstract Material getSmeltingOutput();

}
