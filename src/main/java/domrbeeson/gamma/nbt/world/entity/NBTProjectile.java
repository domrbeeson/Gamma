package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.object.ArrowEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTShort;
import domrbeeson.gamma.nbt.world.NBTEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public abstract class NBTProjectile extends NBTEntity {

    private final int blockX, blockY, blockZ;
    private final byte inBlockId, shake;
    private final boolean inGround;

    public NBTProjectile(NBTCompound compound) {
        super(compound);
        blockX = compound.getShort("xTile").getValue();
        blockY = compound.getShort("yTile").getValue();
        blockZ = compound.getShort("zTile").getValue();
        inBlockId = compound.getByte("inTile").getValue();
        shake = compound.getByte("shake").getValue();
        inGround = compound.getByte("inGround").getValue() == 1;
    }

    public NBTProjectile(String name, Entity<?> entity) {
        super(name, entity);
        blockX = entity.getPos().getBlockX();
        blockY = entity.getPos().getBlockY();
        blockZ = entity.getPos().getBlockZ();
        Block block = entity.getWorld().getBlock(blockX, blockY, blockZ);
        inBlockId = block.id();
        shake = 0; // TODO arrow shake?
        inGround = isOnGround(); // TODO probably different ground check to regular entities
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("xTile", new NBTShort((short) blockX));
        tags.put("yTile", new NBTShort((short) blockY));
        tags.put("zTile", new NBTShort((short) blockZ));
        tags.put("inTile", new NBTByte(inBlockId));
        tags.put("shake", new NBTByte(shake));
        tags.put("inGround", new NBTByte(inGround));

        return tags;
    }

    @Override
    public Entity<?> createEntity(World world) {
        return new ArrowEntity(world, getPos());
    }

    public int getBlockX() {
        return blockX;
    }

    public int getBlockY() {
        return blockY;
    }

    public int getBlockZ() {
        return blockZ;
    }

}
