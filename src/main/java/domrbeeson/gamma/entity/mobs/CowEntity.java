package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.LivingEntityMetadata;
import domrbeeson.gamma.world.World;

public class CowEntity extends LivingEntity<LivingEntityMetadata> {

    public static final String NAME = "Cow";

    public CowEntity(World world, Pos pos) {
        this(world, pos, new LivingEntityMetadata());
    }

    public CowEntity(World world, Pos pos, LivingEntityMetadata meta) {
        super(EntityType.COW, world, pos, meta, new CollisionBox(pos, 0.9, 1.4), 10);
    }

}
