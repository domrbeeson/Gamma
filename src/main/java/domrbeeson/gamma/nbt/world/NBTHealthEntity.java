package domrbeeson.gamma.nbt.world;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTShort;

import java.util.Map;

public abstract class NBTHealthEntity extends NBTEntity {

    private final short health;

    public NBTHealthEntity(NBTCompound compound) {
        super(compound);
        health = compound.getShort("Health").getValue();
    }

    public NBTHealthEntity(String name, Entity<?> entity) {
        super(name, entity);
        health = 0;
    }

    public short getHealth() {
        return health;
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("Health", new NBTShort(health));

        return tags;
    }

}
