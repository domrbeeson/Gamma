package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.ItemEntity;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTShort;
import domrbeeson.gamma.nbt.world.NBTHealthEntity;
import domrbeeson.gamma.world.World;

import java.util.HashMap;
import java.util.Map;

public class NBTItem extends NBTHealthEntity {

    private final short health, age, itemId, damage;
    private final byte amount;

    public NBTItem(NBTCompound compound) {
        super(compound);
        health = compound.getShort("Health").getValue();
        age = compound.getShort("Age").getValue();
        NBTCompound itemCompound = compound.getComound("Item");
        itemId = itemCompound.getShort("id").getValue();
        amount = itemCompound.getByte("Count").getValue();
        damage = itemCompound.getShort("Damage").getValue();
    }

    public NBTItem(Entity<?> entity) {
        super("Item", entity);
        if (entity instanceof ItemEntity item) {
            // TODO figure out how item health works
            health = 5;
            age = (short) (entity.getWorld().getTime() - item.getSpawnTime());
            itemId = item.getItem().id();
            amount = item.getItem().amount();
            damage = item.getItem().metadata();
        } else {
            health = 5;
            age = 0;
            itemId = Material.STONE.id;
            amount = 1;
            damage = 0;
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("Health", new NBTShort(health));
        tags.put("Age", new NBTShort(age));

        Map<String, NBTTag> itemTags = new HashMap<>();
        itemTags.put("id", new NBTShort(itemId));
        itemTags.put("Count", new NBTByte(amount));
        itemTags.put("Damage", new NBTShort(damage));
        tags.put("Item", new NBTCompound(itemTags));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new ItemEntity(world, getPos(), Material.get(itemId, damage).getItem(amount));
    }
}
