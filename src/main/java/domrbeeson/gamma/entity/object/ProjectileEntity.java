package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class ProjectileEntity extends ObjectEntity {

    private byte inTile, shake;
    private @Nullable Entity<?> shooter;

    public ProjectileEntity(EntityType type, World world, Pos pos, @Nullable Entity<?> shooter) {
        super(type, world, pos, new CollisionBox(pos, 0.25, 0.25)); // TODO verify collision box radius
    }

    public boolean hasShooter() {
        return shooter != null;
    }

    public @Nullable Entity<?> getShooter() {
        return shooter;
    }

    public void setShooter(@Nullable Entity<?> shooter) {
        this.shooter = shooter;
    }

    public void clearShooter() {
        setShooter(null);
    }

}
