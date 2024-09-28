package domrbeeson.gamma.nbt.world.tile;

import domrbeeson.gamma.block.tile.PistonTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTFloat;
import domrbeeson.gamma.nbt.tags.NBTInt;
import domrbeeson.gamma.nbt.world.NBTTileEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTPistonTile extends NBTTileEntity {

    private final short blockId, blockMeta;
    private final byte facing;
    private final float progress;
    private final boolean extending;

    public NBTPistonTile(NBTCompound compound) {
        super(compound);
        blockId = (short) compound.getInt("blockId").getValue();
        blockMeta = (short) compound.getInt("blockData").getValue();
        facing = (byte) compound.getInt("facing").getValue();
        progress = compound.getFloat("facing").getValue();
        extending = compound.getByte("extending").getValue() == 1;
    }

    public NBTPistonTile(TileEntity tileEntity) {
        super(PISTON_NAME, tileEntity);
        if (tileEntity instanceof PistonTileEntity) {
            // TODO read piston tile entity
            blockId = 0;
            blockMeta = 0;
            facing = 0;
            progress = 0f;
            extending = false;
        } else {
            blockId = 0;
            blockMeta = 0;
            facing = 0;
            progress = 0f;
            extending = false;
        }
    }

    @Override
    public TileEntity createTileEntity(World world) {
        return new PistonTileEntity(getX(), getY(), getZ()); // TODO pass piston values
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("blockId", new NBTInt(blockId));
        tags.put("blockData", new NBTInt(blockMeta));
        tags.put("facing", new NBTInt(facing));
        tags.put("progress", new NBTFloat(progress));
        tags.put("extending", new NBTByte(extending));

        return tags;
    }
}
