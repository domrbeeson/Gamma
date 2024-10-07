package domrbeeson.gamma.entity.mobs;

import domrbeeson.gamma.entity.*;
import domrbeeson.gamma.entity.metadata.WolfMetadata;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

public class WolfEntity extends LivingEntity<WolfMetadata> {

    public static final String NAME = "Wolf";

    private boolean angry = false;
    private @Nullable Entity<?> aggressiveTowards = null;

    public WolfEntity(World world, Pos pos) {
        this(world, pos, new WolfMetadata());
    }

    public WolfEntity(World world, Pos pos, WolfMetadata meta) {
        super(EntityType.WOLF, world, pos, meta, new CollisionBox(pos, 0.6, 0.85), meta.isTamed() ? (short) 40 : 8);
    }

    public void setAggressiveTowards(@Nullable Entity<?> entity) {
        aggressiveTowards = entity;
        angry = entity != null;
    }

    public void clearAggression() {
        setAggressiveTowards(null);
    }

    public boolean isAngry() {
        return angry;
    }

    public @Nullable Entity<?> getAggressiveTowards() {
        return aggressiveTowards;
    }

}
