package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.*;
import domrbeeson.gamma.entity.metadata.LivingEntityMetadata;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

public class ZombiePigmanEntity extends LivingEntity<LivingEntityMetadata> {

    public static final String NAME = "PigZombie";

    private @Nullable Entity<?> aggressiveTowards = null;
    private short angerTicks = 0;

    public ZombiePigmanEntity(World world, Pos pos) {
        this(world, pos, new LivingEntityMetadata());
    }

    public ZombiePigmanEntity(World world, Pos pos, LivingEntityMetadata meta) {
        super(EntityType.ZOMBIE_PIGMAN, world, pos, meta, new CollisionBox(pos, 0.6, 1.95), 20);
    }

    public boolean isAngry() {
        return angerTicks > 0;
    }

    public @Nullable Entity<?> getAggressiveTowards() {
        return aggressiveTowards;
    }

    public short getAngerTicks() {
        return angerTicks;
    }

}
