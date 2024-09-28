package domrbeeson.gamma.fuel;

import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class Fuel {

    private static final Map<Short, Fuel> FUEL = new HashMap<>();

    private final short itemId, burnTicks;

    public Fuel(short itemId, int burnTicks) {
        this.itemId = itemId;
        this.burnTicks = (short) burnTicks;
        FUEL.put(itemId, this);
    }

    @Nullable
    public static Fuel get(short id) {
        return FUEL.get(id);
    }

    public final short getItemId() {
        return itemId;
    }

    public final short getBurnTicks() {
        return burnTicks;
    }

    public Item getItemAfterSmelting() {
        return Material.AIR.getItem();
    }
}
