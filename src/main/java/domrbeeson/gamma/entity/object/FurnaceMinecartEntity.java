package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.World;

public class FurnaceMinecartEntity extends MinecartEntity {

    private double pushX = 0;
    private double pushZ = 0;
    private short fuel = 0;

    public FurnaceMinecartEntity(World world, Pos pos) {
        super(EntityType.FURNACE_MINECART, world, pos);
    }

    public double getPushX() {
        return pushX;
    }

    public double getPushZ() {
        return pushZ;
    }

    public short getFuel() {
        return fuel;
    }

}
