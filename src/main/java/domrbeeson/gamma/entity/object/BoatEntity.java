package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;

public class BoatEntity extends ObjectEntity {

    // TODO boat out of water has a different vertical radius of 0.5625

    public static final String NAME = "Boat";

    public BoatEntity(World world, Pos pos) {
        super(EntityType.BOAT, world, pos, new CollisionBox(pos, 1.375, 0.455));
    }

}
