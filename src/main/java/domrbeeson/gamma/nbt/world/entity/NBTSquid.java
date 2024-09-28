package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.mobs.SquidEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

public class NBTSquid extends NBTHealthEntity {

    public NBTSquid(NBTCompound compound) {
        super(compound);
    }

    public NBTSquid(Entity<?> entity) {
        super(SquidEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new SquidEntity(world, getPos());
    }

}
