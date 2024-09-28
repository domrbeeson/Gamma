package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.PigMetadata;
import domrbeeson.gamma.world.World;

public class PigEntity extends LivingEntity<PigMetadata> {

    public static final String NAME = "Pig";

    public PigEntity(World world, Pos pos) {
        this(world, pos, new PigMetadata());
    }

    public PigEntity(World world, Pos pos, PigMetadata meta) {
        super(EntityType.PIG, world, pos, meta, new CollisionBox(pos, 0.9, 0.9), 10);
    }

}
