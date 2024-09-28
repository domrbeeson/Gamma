package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.mobs.GiantZombieEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

public class NBTGiant extends NBTHealthEntity {

    public NBTGiant(NBTCompound compound) {
        super(compound);
    }

    public NBTGiant(Entity<?> entity) {
        super(GiantZombieEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new GiantZombieEntity(world, getPos());
    }

}
