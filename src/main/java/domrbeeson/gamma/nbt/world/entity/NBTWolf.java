package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.metadata.WolfMetadata;
import domrbeeson.gamma.entity.mobs.WolfEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTString;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTWolf extends NBTHealthEntity {

    private final boolean angry, sitting;
    private final String owner;

    public NBTWolf(NBTCompound compound) {
        super(compound);
        angry = compound.getByte("Angry").getValue() == 1;
        sitting = compound.getByte("Sitting").getValue() == 1;
        NBTString owner = compound.getString("Owner");
        if (owner != null) {
            this.owner = owner.getValue();
        } else {
            this.owner = "";
        }
    }

    public NBTWolf(Entity<?> entity) {
        super(WolfEntity.NAME, entity);
        if (entity instanceof WolfEntity wolf) {
            angry = wolf.isAngry();
            sitting = wolf.getMetadata().isSitting();
            owner = wolf.getMetadata().getOwnerUsername();
        } else {
            angry = false;
            sitting = false;
            owner = "";
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("Angry", new NBTByte(angry));
        tags.put("Sitting", new NBTByte(sitting));
        tags.put("Owner", new NBTString(owner));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new WolfEntity(world, getPos(), new WolfMetadata().setHealth(getHealth()).setOwnerUsername(owner).setSitting(sitting));
    }

}
