package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.metadata.SlimeMetadata;
import domrbeeson.gamma.entity.mobs.SlimeEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTInt;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTSlime extends NBTHealthEntity {

    private final int size;

    public NBTSlime(NBTCompound compound) {
        super(compound);
        size = compound.getInt("Size").getValue();
    }

    public NBTSlime(Entity<?> entity) {
        super("Slime", entity);
        if (entity instanceof SlimeEntity slime) {
            size = slime.getMetadata().getSize() + 1;
        } else {
            size = 0;
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("Size", new NBTInt(size));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new SlimeEntity(world, getPos(), new SlimeMetadata().setSize(size));
    }
}
