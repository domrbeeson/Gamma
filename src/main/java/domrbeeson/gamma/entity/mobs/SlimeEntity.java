package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.SlimeMetadata;
import domrbeeson.gamma.world.World;

public class SlimeEntity extends LivingEntity<SlimeMetadata> {

    public static final String NAME = "Slime";

    private static final double SLIME_BASE_SIZE = 0.51;

    public SlimeEntity(World world, Pos pos) {
        this(world, pos, new SlimeMetadata());
    }

    public SlimeEntity(World world, Pos pos, SlimeMetadata meta) {
        super(EntityType.SLIME, world, pos, meta, new CollisionBox(pos, SLIME_BASE_SIZE * meta.getSize(), SLIME_BASE_SIZE * meta.getSize()), 1); // Slime health is 1:1 health:size
    }

}
