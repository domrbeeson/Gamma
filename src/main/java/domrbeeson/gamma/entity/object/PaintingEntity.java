package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.PaintingType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;

public class PaintingEntity extends ObjectEntity {

    public static final String NAME = "Painting";

    private final PaintingType type;
    private final byte direction;

    public PaintingEntity(World world, Pos pos, PaintingType type, byte direction) {
        super(EntityType.PAINTING, world, pos, EMPTY_COLLISION_BOX);
        this.type = type;
        this.direction = direction;
    }

    public byte getDirection() {
        return direction;
    }

    public PaintingType getPaintingType() {
        return type;
    }

}
