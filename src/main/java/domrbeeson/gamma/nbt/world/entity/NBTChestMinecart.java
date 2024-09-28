package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.object.ChestMinecartEntity;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTList;
import domrbeeson.gamma.nbt.tags.NBTShort;
import domrbeeson.gamma.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBTChestMinecart extends NBTMinecart {

    private final Map<Byte, Item> itemSlots = new HashMap<>();

    public NBTChestMinecart(NBTCompound compound) {
        super(compound);
        NBTList items = compound.getList("Items");
        items.getValue().forEach(nbt -> {
            NBTCompound itemCompound = (NBTCompound) nbt;
            byte slot = itemCompound.getByte("Slot").getValue();
            short itemId = itemCompound.getShort("id").getValue();
            byte amount = itemCompound.getByte("Count").getValue();
            short damage = itemCompound.getShort("Damage").getValue();
            itemSlots.put(slot, Material.get(itemId, damage).getItem(amount));
        });
    }

    public NBTChestMinecart(Entity<?> entity) {
        super(entity);
        if (entity instanceof ChestMinecartEntity minecart) {
            // TODO read minecart chest items
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        List<NBTCompound> items = new ArrayList<>();
        itemSlots.forEach((slot, item) -> {
            Map<String, NBTTag> itemTags = new HashMap<>();
            itemTags.put("Slot", new NBTByte(slot));
            itemTags.put("id", new NBTShort(item.id()));
            itemTags.put("Count", new NBTByte(item.amount()));
            itemTags.put("Damage", new NBTShort(item.metadata()));
            items.add(new NBTCompound(itemTags));
        });
        tags.put("Items", new NBTCompound(tags));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new ChestMinecartEntity(world, getPos());
    }
}
