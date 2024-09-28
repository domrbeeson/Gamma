package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.SheepMetadata;
import domrbeeson.gamma.world.World;

public class SheepEntity extends LivingEntity<SheepMetadata> {

    public static final String NAME = "Sheep";

    public SheepEntity(World world, Pos pos) {
        this(world, pos, new SheepMetadata());
    }

    public SheepEntity(World world, Pos pos, SheepMetadata meta) {
        super(EntityType.SHEEP, world, pos, meta, new CollisionBox(pos, 0.9, 1.3), 8);
    }

}
