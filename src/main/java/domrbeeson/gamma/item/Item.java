package domrbeeson.gamma.item;

import domrbeeson.gamma.player.Player;

public class Item {

    public static final Item AIR = new Item((short) 0, (short) 0, 0);

    private final short id, metadata;
    private final byte amount;

    protected Item(Material material) {
        this(material, 1);
    }

    protected Item(Material material, int amount) {
        this(material.id, material.metadata, (byte) amount);
    }

    protected Item(short id, short metadata) {
        this(id, metadata, (byte) 1);
    }

    protected Item(short id, short metadata, int amount) {
        this.id = id;
        this.metadata = metadata;
        this.amount = (byte) amount;
    }

    public short id() {
        return id;
    }

    public short metadata() {
        return metadata;
    }

    public byte amount() {
        return amount;
    }

    public void use(Player player) {

    }

    @Override
    public String toString() {
        return "[id=" + id + ",metadata=" + metadata + ",amount=" + amount + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item item)) {
            return false;
        }
        return item.id == id
                && item.metadata == metadata
                && item.amount == amount;
    }

}
