package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.object.FallingBlockEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTFallingBlock extends NBTEntity {

    private final byte blockId;

    public NBTFallingBlock(NBTCompound compound) {
        super(compound);
        blockId = compound.getByte("Tile").getValue();
    }

    public NBTFallingBlock(Entity<?> entity) {
        super("FallingSand", entity);
        if (entity instanceof FallingBlockEntity block) {
            blockId = block.getBlockId();
        } else {
            blockId = 0;
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("Tile", new NBTByte(blockId));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new FallingBlockEntity(world, getPos(), blockId);
    }
}
