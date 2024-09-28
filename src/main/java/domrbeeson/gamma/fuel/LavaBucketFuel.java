package domrbeeson.gamma.fuel;

import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;

public class LavaBucketFuel extends Fuel {

    private static final Item SMELTING_ITEM = Material.BUCKET.getItem();

    public LavaBucketFuel() {
        super(Material.LAVA_BUCKET.id, 20_000);
    }

    @Override
    public Item getItemAfterSmelting() {
        return SMELTING_ITEM;
    }

}
