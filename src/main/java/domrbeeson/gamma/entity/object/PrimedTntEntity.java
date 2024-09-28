package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;

public class PrimedTntEntity extends ObjectEntity {

    public static final String NAME = "PrimedTnt";

    private byte fuseTicks = 0;

    public PrimedTntEntity(World world, Pos pos) {
        this(world, pos, (byte) 80);
    }

    public PrimedTntEntity(World world, Pos pos, byte fuseTicks) {
        super(EntityType.PRIMED_TNT, world, pos, new CollisionBox(pos, 0.98, 0.98));
        this.fuseTicks = fuseTicks;
    }

    public byte getFuseTicks() {
        return fuseTicks;
    }

    @Override
    public void tick(long ticks) {
        super.tick(ticks);

        fuseTicks--;
        if (fuseTicks <= 0) {
            remove();
            // TODO explosion
        }
    }

}
