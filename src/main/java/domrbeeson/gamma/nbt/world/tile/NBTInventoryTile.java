package domrbeeson.gamma.nbt.world.tile;

import domrbeeson.gamma.block.tile.InventoryTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTList;
import domrbeeson.gamma.nbt.tags.NBTShort;
import domrbeeson.gamma.nbt.world.NBTTileEntity;
import domrbeeson.gamma.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBTInventoryTile extends NBTTileEntity {

    private final Item[] slots;

    public NBTInventoryTile(NBTCompound compound) {
        super(compound);
        NBTList items = compound.getList("Items");
        slots = new Item[items.getValue().size()];
        items.getValue().forEach(tag -> {
            NBTCompound itemCompound = (NBTCompound) tag;
            byte slot = itemCompound.getByte("Slot").getValue();
            short id = itemCompound.getShort("id").getValue();
            byte amount = itemCompound.getByte("Count").getValue();
            short metadata = itemCompound.getShort("Damage").getValue();
            slots[slot] = Material.get(id, metadata).getItem(amount);
        });
    }

    public NBTInventoryTile(String name, TileEntity tileEntity) {
        super(name, tileEntity);
        if (tileEntity instanceof InventoryTileEntity<?> inventory) {
            slots = inventory.getInventory().getSlots();
        } else {
            slots = new Item[0];
        }
    }

    @Override
    public TileEntity createTileEntity(World world) {
        return null;
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        List<NBTTag> items = new ArrayList<>();
        for (int i = 0; i < slots.length; i++) {
            Map<String, NBTTag> item = new HashMap<>();
            item.put("Slot", new NBTByte(i));
            item.put("id", new NBTShort(slots[i].id()));
            item.put("Count", new NBTByte(slots[i].amount()));
            item.put("Damage", new NBTShort(slots[i].metadata()));
            items.add(i, new NBTCompound(item));
        }
        tags.put("Items", new NBTList(items));

        return tags;
    }
}
