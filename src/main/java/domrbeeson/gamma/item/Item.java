package domrbeeson.gamma.item;

public class Item {

    private static final Item AIR = new Item((short) 0, (short) 0, (byte) 0);

    public static Item getAir() {
        return AIR.clone();
    }

    private short id;
    private short metadata;
    private byte amount;

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
        this(material.id, material.metadata, amount);
    }

    public Item(short id, short metadata, int amount) {
        if (id <= 0) {
            id = 0;
            amount = 0;
        } else if (amount < 1) {
            amount = 1;
        }
        byte maxStack = getMaterial().maxStack;
        if (amount > maxStack) {
            amount = maxStack;
        }
        this.id = id;
        this.metadata = metadata;
        this.amount = (byte) amount;
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

    public void setMetadata(short metadata) {
        this.metadata = metadata;
        // TODO update inventories somehow
    }

    public void setIdAndMetadata(short id, short metadata) {
        this.id = id;
        this.metadata = metadata;
        // TODO update inventories somehow
    }

    public void addAmount(int amount) {
        this.amount += (byte) amount;
        if (this.amount <= 0) {
            this.id = 0;
            this.metadata = 0;
            this.amount = 0;
        } else if (this.amount > getMaterial().maxStack) {
            this.amount = getMaterial().maxStack;
        }
    }

    public void setAmount(int amount) {
        this.amount = (byte) amount;
        if (amount <= 0) {
            this.id = 0;
            this.metadata = 0;
            this.amount = 0;
        } else if (this.amount > getMaterial().maxStack) {
            this.amount = getMaterial().maxStack;
        }
    }

    public Material getMaterial() {
        return Material.get(id, metadata);
    }

    public ItemHandler getItemHandler() {
        return ItemHandlers.getItemHandler(id);
    }

    public Item clone() {
        return new Item(id, metadata, amount);
    }

}
