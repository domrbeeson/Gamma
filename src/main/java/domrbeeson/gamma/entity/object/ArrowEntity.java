package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

public class ArrowEntity extends ProjectileEntity {

    public static final String NAME = "Arrow";

    public ArrowEntity(World world, Pos pos) {
        this(world, pos, null);
    }

    public ArrowEntity(World world, Pos pos, @Nullable Entity<?> shooter) {
        super(EntityType.ARROW, world, pos, shooter);
    }

}
