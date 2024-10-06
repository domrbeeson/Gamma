package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.LivingEntityMetadata;
import domrbeeson.gamma.world.World;

public class GiantZombieEntity extends LivingEntity<LivingEntityMetadata> {

    public static final String NAME = "Giant";

    public GiantZombieEntity(World world, Pos pos) {
        this(world, pos, new LivingEntityMetadata());
    }

    public GiantZombieEntity(World world, Pos pos, LivingEntityMetadata meta) {
        super(EntityType.GIANT_ZOMBIE, world, pos, meta, new CollisionBox(pos, 3.6, 11.7), (short) 100);
    }

}
