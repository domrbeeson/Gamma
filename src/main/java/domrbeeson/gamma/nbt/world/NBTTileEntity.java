package domrbeeson.gamma.nbt.world;

import domrbeeson.gamma.block.tile.*;
import domrbeeson.gamma.nbt.NBTCompoundCreator;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.tags.NBTInt;
import domrbeeson.gamma.nbt.tags.NBTString;
import domrbeeson.gamma.nbt.world.tile.*;
import domrbeeson.gamma.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class NBTTileEntity implements NBTCompoundCreator {

    protected static final String FURNACE_NAME = "Furnace";
    protected static final String CHEST_NAME = "Chest";
    protected static final String JUKEBOX_NAME = "RecordPlayer";
    protected static final String DISPENSER_NAME = "Trap";
    protected static final String SIGN_NAME = "Sign";
    protected static final String SPAWNER_NAME = "MobSpawner";
    protected static final String NOTE_BLOCK_NAME = "Music";
    protected static final String PISTON_NAME = "Piston";

    private static final Map<Class<? extends TileEntity>, Function<TileEntity, NBTTileEntity>> TILE_TO_NBT = new HashMap<>() {{
        put(FurnaceTileEntity.class, NBTFurnaceTile::new);
        put(ChestTileEntity.class, NBTChestTile::new);
        put(JukeboxTileEntity.class, NBTJukeboxTile::new);
        put(DispenserTileEntity.class, NBTDispenserTile::new);
        put(SignTileEntity.class, NBTSignTile::new);
        put(SpawnerTileEntity.class, NBTSpawnerTile::new);
        put(NoteBlockTileEntity.class, NBTNoteBlockTile::new);
        put(PistonTileEntity.class, NBTPistonTile::new);
    }};
    private static final Map<String, Function<NBTCompound, NBTTileEntity>> NAME_TO_NBT = new HashMap<>() {{
        put(FURNACE_NAME, NBTFurnaceTile::new);
        put(CHEST_NAME, NBTChestTile::new);
        put(JUKEBOX_NAME, NBTJukeboxTile::new);
        put(DISPENSER_NAME, NBTDispenserTile::new);
        put(SIGN_NAME, NBTSignTile::new);
        put(SPAWNER_NAME, NBTSpawnerTile::new);
        put(NOTE_BLOCK_NAME, NBTNoteBlockTile::new);
        put(PISTON_NAME, NBTPistonTile::new);
    }};

    private final String name;
    private final int x, y, z;

    public NBTTileEntity(NBTCompound compound) {
        name = compound.getString("id").getValue();

        x = compound.getInt("x").getValue();
        y = compound.getInt("y").getValue();
        z = compound.getInt("z").getValue();
    }

    public NBTTileEntity(String name, TileEntity tileEntity) {
        this.name = name;
        x = tileEntity.getX();
        y = tileEntity.getY();
        z = tileEntity.getZ();
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = new HashMap<>();

        tags.put("id", new NBTString(name));
        tags.put("x", new NBTInt(x));
        tags.put("y", new NBTInt(y));
        tags.put("z", new NBTInt(z));

        return tags;
    }

    public static NBTTileEntity createNBTEntity(TileEntity tile) {
        return TILE_TO_NBT.get(tile.getClass()).apply(tile);
    }

    public static NBTTileEntity createNBTEntity(NBTCompound compound) {
        String name = compound.getString("id").getValue();
        return NAME_TO_NBT.get(name).apply(compound);
    }

    public abstract TileEntity createTileEntity(World world);

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final int getZ() {
        return z;
    }

}
