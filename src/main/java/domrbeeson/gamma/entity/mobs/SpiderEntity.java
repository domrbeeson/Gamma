package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.LivingEntityMetadata;
import domrbeeson.gamma.world.World;

public class SpiderEntity extends LivingEntity<LivingEntityMetadata> {

    public static final String NAME = "Spider";

    public SpiderEntity(World world, Pos pos) {
        this(world, pos, new LivingEntityMetadata());
    }

    public SpiderEntity(World world, Pos pos, LivingEntityMetadata meta) {
        super(EntityType.SPIDER, world, pos, meta, new CollisionBox(pos, 1.4, 0.9), (short) 16);
    }

}
