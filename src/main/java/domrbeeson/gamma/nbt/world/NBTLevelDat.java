package domrbeeson.gamma.nbt.world;

import domrbeeson.gamma.Saveable;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.nbt.NBTCompoundCreator;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.*;
import domrbeeson.gamma.world.Dimension;
import domrbeeson.gamma.world.format.NotchianWorldFormat;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class NBTLevelDat implements Saveable, NBTCompoundCreator {

	private final File levelDatFile;
	private final long seed;
	private final Dimension dimension;
	private final int version;
	private final String name;
	private final @Nullable NBTTag player;
	private long time, sizeOnDisk, lastPlayed;
	private Pos spawn;
	private boolean raining = false;
	private int rainTime = 0;
	private boolean thundering = false;
	private int thunderTime = 0;
	private boolean snowCovered = false;

	public NBTLevelDat(File levelDatFile, String name, NotchianWorldFormat format) {
		this.levelDatFile = levelDatFile;
		seed = ThreadLocalRandom.current().nextLong();
		dimension = Dimension.OVERWORLD;
		version = format.getVersion();
		this.name = name;
		player = null;
		spawn = new Pos(0, 100, 0); // TODO find spawn
		time = 0;
		sizeOnDisk = 0;
		lastPlayed = 0;
	}

	public NBTLevelDat(File levelDatFile, NBTCompound compound) {
		if (compound == null) {
			System.out.println("Corrupt level.dat file!");
			System.exit(1);
		}

		NBTCompound dataCompound = (NBTCompound) compound.getValue().get("Data");
		if (dataCompound == null) {
			System.out.println("Corrupt level.dat file!");
			System.exit(1);
		}

		this.levelDatFile = levelDatFile;

		Map<String, NBTTag> values = dataCompound.getValue();

		seed = ((NBTLong) values.get("RandomSeed")).getValue();
		int spawnX = ((NBTInt) values.get("SpawnX")).getValue();
		int spawnY = ((NBTInt) values.get("SpawnY")).getValue();
		int spawnZ = ((NBTInt) values.get("SpawnZ")).getValue();
		spawn = new Pos(spawnX, spawnY, spawnZ);
		time = ((NBTLong) values.get("Time")).getValue();
		sizeOnDisk = ((NBTLong) values.get("SizeOnDisk")).getValue();
		lastPlayed = ((NBTLong) values.get("LastPlayed")).getValue();
		NBTTag dimensionNbt = values.get("Dimension");
		if (dimensionNbt != null) {
			dimension = Dimension.getById(((NBTByte) dimensionNbt).getValue());
		} else {
			dimension = Dimension.OVERWORLD;
		}
		NBTTag version = values.get("version");
		if (version != null) {
			this.version = ((NBTInt) version).getValue();
		} else {
			this.version = 0;
		}
		NBTTag name = values.get("LevelName");
		if (name != null) {
			this.name = ((NBTString) name).getValue();
		} else {
			this.name = null;
		}
		NBTTag rainTime = values.get("rainTime");
		if (rainTime != null) {
			this.rainTime = ((NBTInt) rainTime).getValue();
		}
		NBTTag raining = values.get("rain");
		if (raining != null) {
			this.raining = ((NBTByte) raining).getValue() == 1;
		}
		NBTTag thunderTime = values.get("thunderTime");
		if (thunderTime != null) {
			this.thunderTime = ((NBTInt) thunderTime).getValue();
		}
		NBTTag thundering = values.get("thundering");
		if (thundering != null) {
			this.thundering = ((NBTByte) thundering).getValue() == 1;
		}
		NBTTag snowCovered = values.get("SnowCovered");
		if (snowCovered != null) {
			this.snowCovered = ((NBTByte) snowCovered).getValue() == 1;
		}
		player = values.get("Player"); // Single player world support
	}

	public Map<String, NBTTag> createCompoundTags() {
		Map<String, NBTTag> values = new HashMap<>();

		values.put("RandomSeed", new NBTLong(seed));
		values.put("SpawnX", new NBTInt(spawn.getBlockX()));
		values.put("SpawnY", new NBTInt(spawn.getBlockY()));
		values.put("SpawnZ", new NBTInt(spawn.getBlockZ()));
		values.put("Time", new NBTLong(time));
		values.put("SizeOnDisk", new NBTLong(sizeOnDisk));
		values.put("LastPlayed", new NBTLong(lastPlayed));
		values.put("Dimension", new NBTByte(dimension.id));
		values.put("SnowCovered", new NBTByte(snowCovered));
		if (version > 0) {
			values.put("version", new NBTInt(version));
		}
		if (name != null) {
			values.put("LevelName", new NBTString(name));
		}
		if (player != null) {
			values.put("Player", player);
		}

		return values;
	}

	@Override
	public void save() {
		// TODO level.dat_old
		Map<String, NBTTag> dataTag = new HashMap<>();
		dataTag.put("Data", new NBTCompound(createCompoundTags()));
		new NBTCompound(dataTag).saveToFile(levelDatFile);
	}

	public long getSeed() {
		return seed;
	}

	public Pos getSpawnPos() {
		return spawn;
	}

	public long getTime() {
		return time;
	}

	public long getSizeOnDisk() {
		return sizeOnDisk;
	}

	public long getLastPlayedMillis() {
		return lastPlayed;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setSpawnPos(Pos spawn) {
		this.spawn = spawn;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setSizeOnDisk(long sizeOnDisk) {
		this.sizeOnDisk = sizeOnDisk;
	}

	public void updateLastPlayed() {
		lastPlayed = System.currentTimeMillis();
	}

	public int getVersion() {
		return version;
	}

	public boolean isSnowCovered() {
		return snowCovered;
	}

	// TODO get/set raining, thundering

}
