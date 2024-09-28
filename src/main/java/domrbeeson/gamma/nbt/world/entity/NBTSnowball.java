package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.object.SnowballEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTEntity;
import domrbeeson.gamma.world.World;

public class NBTSnowball extends NBTEntity {

    public NBTSnowball(NBTCompound compound) {
        super(compound);
    }

    public NBTSnowball(Entity<?> entity) {
        super(SnowballEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new SnowballEntity(world, getPos());
    }

}
