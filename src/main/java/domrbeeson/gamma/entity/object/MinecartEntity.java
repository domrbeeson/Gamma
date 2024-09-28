package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;

public class MinecartEntity extends ObjectEntity {

    public static final String NAME = "Minecart";

    public MinecartEntity(World world, Pos pos) {
        this(EntityType.MINECART, world, pos);
    }

    protected MinecartEntity(EntityType type, World world, Pos pos) {
        super(type, world, pos, new CollisionBox(pos, 0.98, 0.7));
    }

}
