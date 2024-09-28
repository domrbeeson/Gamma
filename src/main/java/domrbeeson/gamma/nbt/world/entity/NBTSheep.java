package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.mobs.SheepEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

public class NBTSheep extends NBTHealthEntity {

    public NBTSheep(NBTCompound compound) {
        super(compound);
    }

    public NBTSheep(Entity<?> entity) {
        super(SheepEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new SheepEntity(world, getPos());
    }
}
