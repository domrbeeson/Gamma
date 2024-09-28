package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

public class SnowballEntity extends ProjectileEntity {

    public static final String NAME = "Snowball";

    public SnowballEntity(World world, Pos pos) {
        this(world, pos, null);
    }

    public SnowballEntity(World world, Pos pos, @Nullable Entity<?> shooter) {
        super(EntityType.SNOWBALL, world, pos, shooter);
    }

}
