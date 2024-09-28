package domrbeeson.gamma.world.format;

import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTLevelDat;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public abstract class NotchianWorldFormat implements WorldFormat {

    private World world;
    private File worldFolder = null;
    private NBTLevelDat levelDat = null;

    public abstract int getVersion();

    private File getLevelDatFile(World world) {
        worldFolder = new File(world.getName());
        return new File(worldFolder, "level.dat");
    }

    @Nullable
    private NBTLevelDat getLevelDat(World world) {
        File levelDatFile = getLevelDatFile(world);
        if (!levelDatFile.exists()) {
            return null;
        }
        return new NBTLevelDat(levelDatFile, (NBTCompound) NBTTag.loadFromFile(levelDatFile));
    }

    @Override
    public boolean exists(World world) {
        return getLevelDat() != null;
    }

    @Override
    public void create(World world) {
        getWorldFolder(world).mkdirs();
        levelDat = new NBTLevelDat(getLevelDatFile(world), world.getName() ,this);
        this.world = world;
        save();
    }

    @Override
    public void load(World world) {
        this.world = world;
        levelDat = getLevelDat(world);
    }

    @Override
    public void save() {
        levelDat.save();
        int saved = 0;
        for (Chunk chunk : world.getAndClearChangedChunks()) {
            writeChunk(chunk);
            saved++;
        }
        System.out.println("Saved " + saved + " chunks");
    }

    protected final World getWorld() {
        return world;
    }

    public final File getWorldFolder(World world) {
        if (worldFolder == null) {
            worldFolder = new File(world.getName());
        }
        return worldFolder;
    }

    public final NBTLevelDat getLevelDat() {
        return levelDat;
    }

    public static int getBlockIndex(int x, int y, int z) {
        return getBlockIndex(Block.getChunkRelativeX(x), y, Block.getChunkRelativeZ(z));
    }

    public static int getBlockIndex(byte relativeX, int y, byte relativeZ) {
        return y + (relativeZ * Chunk.HEIGHT + (relativeX * Chunk.HEIGHT * Chunk.WIDTH));
    }

    @Override
    public Pos getSpawn() {
//        return levelDat.getSpawnPos(); // TODO calculate spawn position
        return new Pos(0, 65 ,0);
    }

    @Override
    public void setSpawn(Pos spawn) {
        levelDat.setSpawnPos(spawn);
    }

}
