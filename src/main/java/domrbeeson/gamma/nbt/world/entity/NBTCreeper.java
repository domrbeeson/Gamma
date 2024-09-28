package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.metadata.CreeperMetadata;
import domrbeeson.gamma.entity.mobs.CreeperEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTCreeper extends NBTHealthEntity {

    private final boolean powered;

    public NBTCreeper(NBTCompound compound) {
        super(compound);
        if (compound.hasField("powered")) { // Powered creepers were introduced in beta 1.5
            powered = compound.getByte("powered").getValue() == 1;
        } else {
            powered = false;
        }
    }

    public NBTCreeper(Entity<?> entity) {
        super("Creeper", entity);
        if (entity instanceof CreeperEntity creeper) {
            powered = creeper.getMetadata().isCharged();
        } else {
            powered = false;
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("powered", new NBTByte(powered));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new CreeperEntity(world, getPos(), new CreeperMetadata().setCharged(powered));
    }

}
