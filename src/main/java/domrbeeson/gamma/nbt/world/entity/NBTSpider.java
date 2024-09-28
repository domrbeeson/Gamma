package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.mobs.SpiderEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

public class NBTSpider extends NBTHealthEntity {

    public NBTSpider(NBTCompound compound) {
        super(compound);
    }

    public NBTSpider(Entity<?> entity) {
        super(SpiderEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
    return new SpiderEntity(world, getPos());
    }

}
