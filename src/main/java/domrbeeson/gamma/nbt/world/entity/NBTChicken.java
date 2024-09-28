package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.mobs.ChickenEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

public class NBTChicken extends NBTHealthEntity {

    public NBTChicken(NBTCompound compound) {
        super(compound);
    }

    public NBTChicken(Entity<?> entity) {
        super(ChickenEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new ChickenEntity(world, getPos());
    }

}
