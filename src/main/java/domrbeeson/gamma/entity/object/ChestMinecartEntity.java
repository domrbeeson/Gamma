package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;

public class ChestMinecartEntity extends MinecartEntity {

//    private final Inventory chest; // TODO minecart chest

    public ChestMinecartEntity(World world, Pos pos) {
        super(EntityType.CHEST_MINECART, world, pos);
    }

}
