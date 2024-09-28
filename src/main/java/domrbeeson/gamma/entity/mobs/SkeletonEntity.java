package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.LivingEntityMetadata;
import domrbeeson.gamma.world.World;

public class SkeletonEntity extends LivingEntity<LivingEntityMetadata> {

    public static final String NAME = "Skeleton";

    public SkeletonEntity(World world, Pos pos) {
        this(world, pos, new LivingEntityMetadata());
    }

    public SkeletonEntity(World world, Pos pos, LivingEntityMetadata meta) {
        super(EntityType.SKELETON, world, pos, meta, new CollisionBox(pos, 0.6, 1.99), 20);
    }

}
