package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.object.PrimedTntEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTPrimedTnt extends NBTEntity {

    private final byte fuseTicks;

    public NBTPrimedTnt(NBTCompound compound) {
        super(compound);
        fuseTicks = compound.getByte("Fuse").getValue();
    }

    public NBTPrimedTnt(Entity<?> entity) {
        super("PrimedTnt", entity);
        if (entity instanceof PrimedTntEntity tnt) {
            fuseTicks = tnt.getFuseTicks();
        } else {
            fuseTicks = 80;
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("Fuse", new NBTByte(fuseTicks));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new PrimedTntEntity(world, getPos(), fuseTicks);
    }

}
