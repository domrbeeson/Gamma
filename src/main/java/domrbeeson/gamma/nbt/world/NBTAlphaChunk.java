package domrbeeson.gamma.nbt.world;

import domrbeeson.gamma.Saveable;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.nbt.NBTCompoundCreator;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.*;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.format.NotchianWorldFormat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBTAlphaChunk implements Saveable, NBTCompoundCreator {

    private final File chunkFile;
    private final int xPos, zPos;
    private final byte[] blocks, metadata, skyLight, blockLight, heightMap;
    private final long lastUpdate;
    private final boolean terrainPopulated;
    private final List<NBTTag> entities;
    private final List<NBTTag> tileEntities;

    public NBTAlphaChunk(File chunkFile, NBTCompound compound) {
        this.chunkFile = chunkFile;

        Map<String, NBTTag> level = ((NBTCompound) compound.getValue().get("Level")).getValue();

        xPos = ((NBTInt) level.get("xPos")).getValue();
        zPos = ((NBTInt) level.get("zPos")).getValue();
        blocks = ((NBTBytes) level.get("Blocks")).getValue();
        metadata = ((NBTBytes) level.get("Data")).getValue();
        skyLight = ((NBTBytes) level.get("BlockLight")).getValue();
        blockLight = ((NBTBytes) level.get("SkyLight")).getValue();
        heightMap = ((NBTBytes) level.get("SkyLight")).getValue();
        lastUpdate = ((NBTLong) level.get("LastUpdate")).getValue();
        terrainPopulated = ((NBTByte) level.get("TerrainPopulated")).getValue() == 1;
        entities = ((NBTList) level.get("Entities")).getValue();
        tileEntities = ((NBTList) level.get("TileEntities")).getValue();
    }

    public NBTAlphaChunk(File chunkFile, Chunk chunk) {
        this.chunkFile = chunkFile;
        xPos = chunk.getChunkX();
        zPos = chunk.getChunkZ();
        lastUpdate = chunk.getWorld().getTime();
        terrainPopulated = true; // TODO
        heightMap = new byte[256]; // TODO Compute height map (maybe do this when blocks are placed instead?)

        Block[][][] chunkBlocks = chunk.getBlocks();
        blocks = new byte[Chunk.WIDTH * Chunk.HEIGHT * Chunk.WIDTH];
        metadata = new byte[blocks.length / 2];
        blockLight = new byte[metadata.length];
        skyLight = new byte[metadata.length];

        int index, halfIndex;
        for (byte relativeX = 0; relativeX < chunkBlocks.length; relativeX++) {
            for (int y = 0; y < chunkBlocks[relativeX].length; y++) {
                for (byte relativeZ = 0; relativeZ < chunkBlocks[relativeX][y].length; relativeZ++) {
                    index = NotchianWorldFormat.getBlockIndex(relativeX, y, relativeZ);
                    blocks[index] = chunk.getBlockId(relativeX, y, relativeZ);

                    halfIndex = (int)Math.floor(index / 2d);
                    if (index % 2 == 0) {
                        metadata[halfIndex] = chunk.getBlockMetadata(relativeX, y, relativeZ);
                        blockLight[halfIndex] = chunk.getBlockLight(relativeX, y, relativeZ);
                        skyLight[halfIndex] = chunk.getSkyLight(relativeX, y, relativeZ);
                    } else {
                        metadata[halfIndex] = (byte)(metadata[halfIndex] << 4 | chunk.getBlockMetadata(relativeX, y, relativeZ) & 15);
                        blockLight[halfIndex] = (byte)(blockLight[halfIndex] << 4 | chunk.getBlockLight(relativeX, y, relativeZ) & 15);
                        skyLight[halfIndex] = (byte)(skyLight[halfIndex] << 4 | chunk.getSkyLight(relativeX, y, relativeZ) & 15);
                    }
                    // TODO blockLight
                    // TODO skyLight
                }
            }
        }

        entities = new ArrayList<>();
        chunk.getEntities().forEach(entity -> {
            if (entity instanceof Player) {
                return;
            }
            entities.add(new NBTCompound(NBTEntity.createNBTEntity(entity).createCompoundTags()));
        });

        tileEntities = new ArrayList<>();
        chunk.getTileEntities().forEach(tile -> {
            tileEntities.add(new NBTCompound(NBTTileEntity.createNBTEntity(tile).createCompoundTags()));
        });
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = new HashMap<>();

        tags.put("BlockLight", new NBTBytes(blockLight)); // TODO
        tags.put("Blocks", new NBTBytes(blocks));
        tags.put("Data", new NBTBytes(metadata));
        tags.put("Entities", new NBTList(entities));
        tags.put("HeightMap", new NBTBytes(new byte[256])); // TODO
        tags.put("LastUpdate", new NBTLong(lastUpdate));
        tags.put("SkyLight", new NBTBytes(skyLight)); // TODO
        tags.put("TerrainPopulated", new NBTByte(1)); // TODO
        tags.put("TileEntities", new NBTList(tileEntities));
        tags.put("xPos", new NBTInt(xPos));
        tags.put("zPos", new NBTInt(zPos));

        return tags;
    }

    public int getX() {
        return xPos;
    }

    public int getZ() {
        return zPos;
    }

    public byte[] getBlocks() {
        return blocks;
    }

    public byte[] getBlockData() {
        return metadata;
    }

    public byte[] getSkyLight() {
        return skyLight;
    }

    public byte[] getBlockLight() {
        return blockLight;
    }

    public byte[] getHeightMap() {
        return heightMap;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public boolean isTerrainPopulated() {
        return terrainPopulated;
    }

    public List<NBTTag> getEntities() {
        return entities;
    }

    public List<NBTTag> getTileEntities() {
        return tileEntities;
    }

    @Override
    public void save() {
        Map<String, NBTTag> levelTag = new HashMap<>();
        levelTag.put("Level", new NBTCompound(createCompoundTags()));
        new NBTCompound(levelTag).saveToFile(chunkFile);
    }

}
