package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.PaintingType;
import domrbeeson.gamma.entity.object.PaintingEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTInt;
import domrbeeson.gamma.nbt.tags.NBTString;
import domrbeeson.gamma.nbt.world.NBTEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTPainting extends NBTEntity {

    private final byte direction;
    private final String title;
    private final int tileX, tileY, tileZ;

    public NBTPainting(NBTCompound compound) {
        super(compound);
        direction = compound.getByte("Dir").getValue();
        title = compound.getString("Motive").getValue();
        tileX = compound.getInt("TileX").getValue();
        tileY = compound.getInt("TileY").getValue();
        tileZ = compound.getInt("TileZ").getValue();
    }

    public NBTPainting(Entity<?> entity) {
        super("Painting", entity);
        if (entity instanceof PaintingEntity painting) {
            direction = painting.getDirection();
            title = painting.getPaintingType().title;
            tileX = painting.getPos().getBlockX();
            tileY = painting.getPos().getBlockY();
            tileZ = painting.getPos().getBlockZ();
        } else {
            direction = 0;
            title = PaintingType.KEBAB.title;
            tileX = 0;
            tileY = 0;
            tileZ = 0;
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("Dir", new NBTByte(direction));
        tags.put("Motive", new NBTString(title));
        tags.put("TileX", new NBTInt(tileX));
        tags.put("TileY", new NBTInt(tileY));
        tags.put("TileZ", new NBTInt(tileZ));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new PaintingEntity(world, getPos(), PaintingType.valueOf(title.toUpperCase()), direction);
    }
}
