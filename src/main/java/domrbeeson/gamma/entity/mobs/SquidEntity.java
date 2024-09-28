package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.LivingEntityMetadata;
import domrbeeson.gamma.world.World;

public class SquidEntity extends LivingEntity<LivingEntityMetadata> {

    public static final String NAME = "Squid";

    public SquidEntity(World world, Pos pos) {
        this(world, pos, new LivingEntityMetadata());
    }

    public SquidEntity(World world, Pos pos, LivingEntityMetadata meta) {
        super(EntityType.SQUID, world, pos, meta, new CollisionBox(pos, 0.8, 0.8), 10);
    }

}
