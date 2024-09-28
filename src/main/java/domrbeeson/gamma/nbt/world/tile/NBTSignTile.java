package domrbeeson.gamma.nbt.world.tile;

import domrbeeson.gamma.block.tile.SignTileEntity;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTString;
import domrbeeson.gamma.nbt.world.NBTTileEntity;
import domrbeeson.gamma.world.World;

import java.util.Arrays;
import java.util.Map;

public class NBTSignTile extends NBTTileEntity {

    private final String[] lines;

    public NBTSignTile(NBTCompound compound) {
        super(compound);
        lines = new String[4];
        for (int i = 0; i < 4; i++) {
            NBTString text = compound.getString("Text" + (i + 1));
            String value;
            if (text == null) {
                value = "";
            } else {
                value = text.getValue();
                if (value.length() > SignTileEntity.MAX_LINE_LENGTH) {
                    value = value.substring(0, 15);
                }
            }
            lines[i] = value;
        }
    }

    public NBTSignTile(TileEntity tileEntity) {
        super(SIGN_NAME, tileEntity);
        if (tileEntity instanceof SignTileEntity sign) {
            lines = sign.getLines();
        } else {
            lines = new String[4];
            Arrays.fill(lines, "");
        }
    }

    @Override
    public TileEntity createTileEntity(World world) {
        return new SignTileEntity(getX(), getY(), getZ(), lines);
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        for (int i = 0; i < lines.length; i++) {
            tags.put("Text" + (i + 1), new NBTString(lines[i]));
        }

        return tags;
    }
}
