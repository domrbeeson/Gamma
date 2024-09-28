package domrbeeson.gamma.nbt.world.tile;

import domrbeeson.gamma.block.tile.SpawnerTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTShort;
import domrbeeson.gamma.nbt.tags.NBTString;
import domrbeeson.gamma.nbt.world.NBTEntity;
import domrbeeson.gamma.nbt.world.NBTTileEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTSpawnerTile extends NBTTileEntity {

    private final String entityId;
    private final short delayTicks;

    public NBTSpawnerTile(NBTCompound compound) {
        super(compound);
        entityId = compound.getString("EntityId").getValue();
        delayTicks = compound.getShort("Delay").getValue();
    }

    public NBTSpawnerTile(TileEntity tileEntity) {
        super(SPAWNER_NAME, tileEntity);
        if (tileEntity instanceof SpawnerTileEntity spawner) {
            entityId = NBTEntity.getEntityName(spawner.getEntity());
            delayTicks = spawner.getDelayTicks();
        } else {
            entityId = "Pig";
            delayTicks = 20;
        }
    }

    @Override
    public TileEntity createTileEntity(World world) {
        return new SpawnerTileEntity(getX(), getY(), getZ(), LivingEntity.getEntityFromName(entityId));
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("EntityId", new NBTString(entityId));
        tags.put("Delay", new NBTShort(delayTicks));

        return tags;
    }
}
