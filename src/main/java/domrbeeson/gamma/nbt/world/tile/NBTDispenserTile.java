package domrbeeson.gamma.nbt.world.tile;

import domrbeeson.gamma.block.tile.DispenserTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.inventory.DispenserInventory;
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

public class NBTDispenserTile extends NBTTileEntity {

    private final DispenserInventory inventory;

    public NBTDispenserTile(NBTCompound compound) {
        super(compound);
        inventory = new DispenserInventory();
        compound.getList("Items").getValue().forEach(i -> {
            NBTCompound itemNbt = (NBTCompound) i;
            byte slot = itemNbt.getByte("Slot").getValue();
            byte amount = itemNbt.getByte("Count").getValue();
            short metadata = itemNbt.getShort("Damage").getValue();
            short id = itemNbt.getShort("id").getValue();
            inventory.setSlot(slot, Material.get(id, metadata).getItem(amount));
        });
    }

    public NBTDispenserTile(TileEntity tileEntity) {
        super(DISPENSER_NAME, tileEntity);
        if (tileEntity instanceof DispenserTileEntity dispenser) {
            inventory = dispenser.getInventory();
        } else {
            inventory = new DispenserInventory();
        }
    }

    @Override
    public TileEntity createTileEntity(World world) {
        return new DispenserTileEntity(getX(), getY(), getZ(), inventory);
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String,NBTTag> tags = super.createCompoundTags();

        List<NBTTag> itemsList = new ArrayList<>();
        for (byte slot = 0; slot < inventory.getSlots().length; slot++) {
            Item item = inventory.getSlot(slot);
            short id = item.id();
            if (id <= 0) {
                continue;
            }
            Map<String, NBTTag> itemTag = new HashMap<>();
            itemTag.put("Count", new NBTByte(item.amount()));
            itemTag.put("Damage", new NBTShort(item.metadata()));
            itemTag.put("Slot", new NBTByte(slot));
            itemTag.put("id", new NBTShort(id));
            itemsList.add(new NBTCompound(itemTag));
        }
        tags.put("Items", new NBTList(itemsList));

        return tags;
    }
}
