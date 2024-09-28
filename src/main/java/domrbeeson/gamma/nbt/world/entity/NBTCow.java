package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.mobs.CowEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

public class NBTCow extends NBTHealthEntity {

    public NBTCow(NBTCompound compound) {
        super(compound);
    }

    public NBTCow(Entity<?> entity) {
        super(CowEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new CowEntity(world, getPos());
    }

}
