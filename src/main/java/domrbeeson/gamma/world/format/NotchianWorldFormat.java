package domrbeeson.gamma.world.format;

import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTLevelDat;
import domrbeeson.gamma.nbt.world.NBTPlayer;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public abstract class NotchianWorldFormat implements WorldFormat {

    private World world;
    private File worldFolder = null;
    private File playersFolder = null;
    private NBTLevelDat levelDat = null;

    public abstract int getVersion();

    private File getLevelDatFile(World world) {
        return new File(getWorldFolder(world), "level.dat");
    }

    @Nullable
    private NBTLevelDat getLevelDat(World world) {
        File levelDatFile = getLevelDatFile(world);
        if (!levelDatFile.exists()) {
            return null;
        }
        return new NBTLevelDat(levelDatFile, (NBTCompound) NBTTag.loadFromFile(levelDatFile));
    }

    private File getPlayersFolder(World world) {
        if (playersFolder == null) {
            playersFolder = new File(getWorldFolder(world), "players");
        }
        return playersFolder;
    }

    private File getPlayerDatFile(World world, String username) {
        return new File(getPlayersFolder(world), username + ".dat");
    }

    @Nullable
    private NBTPlayer getPlayerDat(World world, String username) {
        File playerDatFile = getPlayerDatFile(world, username);
        if (!playerDatFile.exists()) {
            return null;
        }
        return new NBTPlayer(playerDatFile, (NBTCompound) NBTTag.loadFromFile(playerDatFile));
    }

    @Override
    public boolean exists(World world) {
        return getLevelDat() != null;
    }

    @Override
    public void create(World world) {
        getWorldFolder(world).mkdirs();
        getPlayersFolder(world).mkdirs();
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
    public Player readPlayer(Player.Builder builder) {
        builder.world(world);

        NBTPlayer nbt = getPlayerDat(world, builder.username);
        if (nbt == null) {
            builder.pos(world.getSpawn());
            return builder.build();
        }

        return builder
                .air(nbt.getAir())
                .attackTime(nbt.getAttackTime())
                .deathTime(nbt.getDeathTime())
                .dimension(nbt.getDimension())
                .fallDistance(nbt.getFallDistance())
                .fireTicks(nbt.getFireTicks())
                .health(nbt.getHealth())
                .hurtTime(nbt.getHurtTime())
                .motion(nbt.getMotion())
                .onGround(nbt.onGround())
                .pos(nbt.getPos())
                .sleepTimer(nbt.getSleepTimer())
                .sleeping(nbt.isSleeping())
                .build();
    }

    @Override
    public void writePlayer(Player player) {
        new NBTPlayer(getPlayerDatFile(world, player.getUsername()), player).save();
    }

    @Override
    public void save() {
        levelDat.save();
        int chunksSaved = 0;
        for (Chunk chunk : world.getAndClearChangedChunks()) {
            writeChunk(chunk);
            chunksSaved++;
        }
        int playersSaved = 0;
        for (Player player : world.getViewers()) {
            new NBTPlayer(getPlayerDatFile(world, player.getUsername()), player).save();
            playersSaved++;
        }
        System.out.println("Saved " + chunksSaved + " chunks and " + playersSaved + " players in world '" + world.getName() + "'");
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
