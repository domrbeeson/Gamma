package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.mobs.SkeletonEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

public class NBTSkeleton extends NBTHealthEntity {

    public NBTSkeleton(NBTCompound compound) {
        super(compound);
    }

    public NBTSkeleton(Entity<?> entity) {
        super(SkeletonEntity.NAME, entity);
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new SkeletonEntity(world, getPos());
    }

}
