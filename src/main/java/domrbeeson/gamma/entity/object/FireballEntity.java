package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

public class FireballEntity extends ProjectileEntity {

    public FireballEntity(World world, Pos pos, @Nullable Entity<?> shooter) {
        super(EntityType.FIREBALL, world, pos, shooter);
    }

}
