package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.metadata.PigMetadata;
import domrbeeson.gamma.entity.mobs.PigEntity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

public class NBTPig extends NBTHealthEntity {

    private final boolean saddle;

    public NBTPig(NBTCompound compound) {
        super(compound);
        saddle = compound.getByte("Saddle").getValue() == 1;
    }

    public NBTPig(Entity<?> entity) {
        super(PigEntity.NAME, entity);
        if (entity instanceof PigEntity pig) {
            saddle = pig.getMetadata().hasSaddle();
        } else {
            saddle = false;
        }
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new PigEntity(world, getPos(), new PigMetadata().setSaddle(saddle));
    }
}
