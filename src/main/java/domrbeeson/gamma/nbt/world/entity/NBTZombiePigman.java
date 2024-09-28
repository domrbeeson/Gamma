package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.mobs.ZombiePigmanEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTShort;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTZombiePigman extends NBTHealthEntity {

    private final short anger;

    public NBTZombiePigman(NBTCompound compound) {
        super(compound);
        anger = compound.getShort("Anger").getValue();
    }

    public NBTZombiePigman(Entity<?> entity) {
        super("PigZombie", entity);
        if (entity instanceof ZombiePigmanEntity zombiePigman) {
            anger = zombiePigman.getAngerTicks();
        } else {
            anger = 0;
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("Anger", new NBTShort(anger));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new ZombiePigmanEntity(world, getPos());
    }

}
