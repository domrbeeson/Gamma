package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.LivingEntityMetadata;
import domrbeeson.gamma.world.World;

public class ChickenEntity extends LivingEntity<LivingEntityMetadata> {

    public static final String NAME = "Chicken";

    public ChickenEntity(World world, Pos pos) {
        this(world, pos, new LivingEntityMetadata());
    }

    public ChickenEntity(World world, Pos pos, LivingEntityMetadata meta) {
        super(EntityType.CHICKEN, world, pos, meta, new CollisionBox(pos, 0.4, 0.7), 4);
    }

}
