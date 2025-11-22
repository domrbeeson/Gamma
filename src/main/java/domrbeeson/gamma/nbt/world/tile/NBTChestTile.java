package domrbeeson.gamma.nbt.world.tile;

import domrbeeson.gamma.block.tile.ChestTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.inventory.ChestInventory;
import domrbeeson.gamma.inventory.InventoryType;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTList;
import domrbeeson.gamma.nbt.tags.NBTShort;
import domrbeeson.gamma.nbt.world.NBTTileEntity;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBTChestTile extends NBTTileEntity {

    private final ChestInventory inventory;

    public NBTChestTile(NBTCompound compound) {
        super(compound);
        inventory = new ChestInventory(InventoryType.CHEST_3_ROWS.ordinal());
        compound.getList("Items").getValue().forEach(i -> {
            NBTCompound itemNbt = (NBTCompound) i;
            byte slot = itemNbt.getByte("Slot").getValue();
            byte amount = itemNbt.getByte("Count").getValue();
            short metadata = itemNbt.getShort("Damage").getValue();
            short id = itemNbt.getShort("id").getValue();
            inventory.setSlot(slot, new Item(id, metadata, amount));
        });
    }

    public NBTChestTile(TileEntity tileEntity) {
        super(CHEST_NAME, tileEntity);
        if (tileEntity instanceof ChestTileEntity chest) {
            inventory = chest.getInventory();
        } else {
            inventory = new ChestInventory(InventoryType.CHEST_3_ROWS.slots);
        }
    }

    @Override
    public TileEntity createTileEntity(World world, Chunk chunk) {
        return new ChestTileEntity(chunk, getX(), getY(), getZ(), inventory);
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String,NBTTag> tags = super.createCompoundTags();

        List<NBTTag> itemsList = new ArrayList<>();
        for (byte slot = 0; slot < inventory.getSlots().length; slot++) {
            Item item = inventory.getSlot(slot);
            short id = item.getId();
            if (id <= 0) {
                continue;
            }
            Map<String, NBTTag> itemTag = new HashMap<>();
            itemTag.put("Count", new NBTByte(item.getAmount()));
            itemTag.put("Damage", new NBTShort(item.getMetadata()));
            itemTag.put("Slot", new NBTByte(slot));
            itemTag.put("id", new NBTShort(id));
            itemsList.add(new NBTCompound(itemTag));
        }
        tags.put("Items", new NBTList(itemsList));

        return tags;
    }
}
