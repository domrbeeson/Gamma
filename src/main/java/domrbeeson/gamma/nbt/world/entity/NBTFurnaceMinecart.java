package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.object.FurnaceMinecartEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTDouble;
import domrbeeson.gamma.nbt.tags.NBTShort;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTFurnaceMinecart extends NBTMinecart {

    private final double pushX, pushZ;
    private final short fuel;

    public NBTFurnaceMinecart(NBTCompound compound) {
        super(compound);
        pushX = compound.getDouble("PushX").getValue();
        pushZ = compound.getDouble("PushZ").getValue();
        fuel = compound.getShort("Fuel").getValue();
    }

    public NBTFurnaceMinecart(Entity<?> entity) {
        super(entity);
        if (entity instanceof FurnaceMinecartEntity minecart) {
            pushX = minecart.getPushX();
            pushZ = minecart.getPushZ();
            fuel = minecart.getFuel();
        } else {
            pushX = 0;
            pushZ = 0;
            fuel = 0;
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("PushX", new NBTDouble(pushX));
        tags.put("PushZ", new NBTDouble(pushZ));
        tags.put("Fuel", new NBTShort(fuel));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new FurnaceMinecartEntity(world, getPos());
    }
}
