package domrbeeson.gamma.nbt.world.tile;

import domrbeeson.gamma.block.tile.JukeboxTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTInt;
import domrbeeson.gamma.nbt.world.NBTTileEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTJukeboxTile extends NBTTileEntity {

    private final short discItemId;

    public NBTJukeboxTile(NBTCompound compound) {
        super(compound);
        discItemId = (short) compound.getInt("Record").getValue();
    }

    public NBTJukeboxTile(TileEntity tileEntity) {
        super(JUKEBOX_NAME, tileEntity);
        if (tileEntity instanceof JukeboxTileEntity jukebox) {
            discItemId = jukebox.getDiscItemId();
        } else {
            discItemId = 0;
        }
    }

    @Override
    public TileEntity createTileEntity(World world) {
        return new JukeboxTileEntity(getX(), getY(), getZ(), discItemId);
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("Record", new NBTInt(discItemId));

        return tags;
    }
}
