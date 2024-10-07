package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.GhastMetadata;
import domrbeeson.gamma.world.World;

public class GhastEntity extends LivingEntity<GhastMetadata> {

    public static final String NAME = "Ghast";

    public GhastEntity(World world, Pos pos) {
        this(world, pos, new GhastMetadata());
    }

    public GhastEntity(World world, Pos pos, GhastMetadata meta) {
        super(EntityType.GHAST, world, pos, meta, new CollisionBox(pos, 4, 4), (short) 10);
    }
    
}
