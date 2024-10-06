package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.CreeperMetadata;
import domrbeeson.gamma.world.World;

public class CreeperEntity extends LivingEntity<CreeperMetadata> {

    public static final String NAME = "Creeper";

    public CreeperEntity(World world, Pos pos) {
        this(world, pos, new CreeperMetadata());
    }

    public CreeperEntity(World world, Pos pos, CreeperMetadata meta) {
        super(EntityType.CREEPER, world, pos, meta, new CollisionBox(pos, 0.6,1.7), (short) 20);
    }

}
