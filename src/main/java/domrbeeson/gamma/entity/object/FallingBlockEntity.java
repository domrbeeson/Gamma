package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;

public class FallingBlockEntity extends ObjectEntity {

    public static final String NAME = "FallingSand";

    private byte blockId;

    public FallingBlockEntity(World world, Pos pos, byte blockId) {
        super(EntityType.SAND, world, pos, new CollisionBox(pos, 0.98, 0.98));
        this.blockId = blockId;
    }

    public byte getBlockId() {
        return blockId;
    }

}
