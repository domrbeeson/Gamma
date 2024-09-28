package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.mobs.ZombieEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

public class NBTZombie extends NBTHealthEntity {

    public NBTZombie(NBTCompound compound) {
        super(compound);
    }

    public NBTZombie(Entity<?> entity) {
        super(ZombieEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new ZombieEntity(world, getPos());
    }

}
