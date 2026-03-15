package domrbeeson.gamma.item;

/*
    Item is a record because updating the id, metadata, or amount of an item does not update it in an inventory,
    which means updating items has to be done via the Inventory methods, which records which slots need updating.
 */
public record Item(
        short id,
        short metadata,
        byte amount
) {

    public static final Item AIR = new Item((short) 0, (short) 0, (byte) 0);

    public Item {
        Material material = Material.get(id, metadata);
        if (material == Material.AIR || amount <= 0) {
            id = 0;
            metadata = 0;
            amount = 0;
        } else if (amount > material.maxStack) {
            amount = material.maxStack;
//        } else if (metadata > material.) { // TODO turn to air if metadata > max metadata (used for items with durability)
//
        }
    }

    public Item(short id) {
        this(id, (short) 0);
    }

    public Item(short id, byte amount) {
        this(id, (short) 0, amount);
    }

    public Item(short id, short metadata) {
        this(id, metadata, (byte) 1);
    }

    public Item(Material material) {
        this(material, (byte) 1);
    }

    public Item(Material material, int amount) {
        this(material.id, material.metadata, (byte) amount);
    }

    public Item(short id, short metadata, int amount) {
        this(id, metadata, (byte) amount);
    }

    public boolean isAir() {
        return id == 0;
    }

    public Item setMetadata(short metadata) {
        return new Item(id, metadata, amount);
    }

    public Item addMetadata(int metadata) {
        return new Item(id, (short) (this.metadata + metadata), amount);
    }

    public Item setIdAndMetadata(short id, short metadata) {
        return new Item(id, metadata, amount);
    }

    public Item addAmount(int amount) {
        return new Item(id, metadata, this.amount + amount);
    }

    public Item setAmount(int amount) {
        return new Item(id, metadata, amount);
    }

    public Material getMaterial() {
        return Material.get(id, metadata);
    }

    public ItemHandler getItemHandler() {
        return ItemHandlers.getItemHandler(id);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item item)) {
            return false;
        }

        return item.id == id && item.metadata == metadata;
    }

}
