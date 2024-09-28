package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.object.ChestMinecartEntity;
import domrbeeson.gamma.entity.object.FurnaceMinecartEntity;
import domrbeeson.gamma.entity.object.MinecartEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTInt;
import domrbeeson.gamma.nbt.world.NBTEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTMinecart extends NBTEntity {

    private final int type;

    public NBTMinecart(NBTCompound compound) {
        super(compound);
        type = compound.getInt("Type").getValue();
    }

    public NBTMinecart(Entity<?> entity) {
        super("Minecart", entity);
        if (entity instanceof ChestMinecartEntity) {
            type = 1;
        } else if (entity instanceof FurnaceMinecartEntity) {
            type = 2;
        } else {
            type = 0;
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("Type", new NBTInt(type));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new MinecartEntity(world, getPos());
    }
}
