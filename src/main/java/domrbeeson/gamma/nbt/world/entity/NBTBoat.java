package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.object.BoatEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTEntity;
import domrbeeson.gamma.world.World;

public class NBTBoat extends NBTEntity {

    public NBTBoat(NBTCompound compound) {
        super(compound);
    }

    public NBTBoat(Entity<?> entity) {
        super(BoatEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new BoatEntity(world, getPos());
    }

}
