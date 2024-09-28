package domrbeeson.gamma.nbt.world.tile;

import domrbeeson.gamma.block.tile.NoteBlockTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTTileEntity;
import domrbeeson.gamma.world.World;

import java.util.Map;

public class NBTNoteBlockTile extends NBTTileEntity {

    private final byte note;

    public NBTNoteBlockTile(NBTCompound compound) {
        super(compound);
        note = compound.getByte("note").getValue();
    }

    public NBTNoteBlockTile(TileEntity tileEntity) {
        super(NOTE_BLOCK_NAME, tileEntity);
        if (tileEntity instanceof NoteBlockTileEntity noteBlock) {
            note = noteBlock.getNote();
        } else {
            note = 0;
        }
    }

    @Override
    public TileEntity createTileEntity(World world) {
        return new NoteBlockTileEntity(getX(), getY(), getZ(), note);
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("note", new NBTByte(note));

        return tags;
    }
}
