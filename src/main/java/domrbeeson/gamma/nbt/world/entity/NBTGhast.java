package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.mobs.GhastEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

public class NBTGhast extends NBTHealthEntity {

    public NBTGhast(NBTCompound compound) {
        super(compound);
    }

    public NBTGhast(Entity<?> entity) {
        super(GhastEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new GhastEntity(world, getPos());
    }

}
