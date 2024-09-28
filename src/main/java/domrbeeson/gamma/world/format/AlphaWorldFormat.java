package domrbeeson.gamma.world.format;

import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.nbt.world.NBTAlphaChunk;
import domrbeeson.gamma.nbt.world.NBTEntity;
import domrbeeson.gamma.nbt.world.NBTTileEntity;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.Dimension;
import domrbeeson.gamma.world.World;

import java.io.File;

public class AlphaWorldFormat extends NotchianWorldFormat {

    private File getChunkFile(World world, int chunkX, int chunkZ) {
        File xFolder = new File(getWorldFolder(world), Integer.toString(chunkX & 63, 36));
        File zFolder = new File(xFolder, Integer.toString(chunkZ & 63, 36));
        zFolder.mkdirs();
        return new File(zFolder, "c." + Integer.toString(chunkX, 36) + "." + Integer.toString(chunkZ, 36) + ".dat");
    }

    @Override
    public boolean readChunk(Chunk.Builder builder) {
        File chunkFile = getChunkFile(builder.world, builder.x, builder.z);
        if (!chunkFile.exists()) {
            return false;
        }

        NBTAlphaChunk chunkNbt = new NBTAlphaChunk(chunkFile, (NBTCompound) NBTTag.loadFromFile(chunkFile));

        int index;
        byte meta, sky, bl;
        byte[] nbtBlocks = chunkNbt.getBlocks();
        byte[] nbtMetadata = chunkNbt.getBlockData();
        byte[] nbtSkyLight = chunkNbt.getSkyLight();
        byte[] nbtBlockLight = chunkNbt.getBlockLight();
        for (byte x = 0; x < Chunk.WIDTH; x++) {
            for (int y = 0; y < Chunk.HEIGHT; y++) {
                for (byte z = 0; z < Chunk.WIDTH; z++) {
                    index = NotchianWorldFormat.getBlockIndex(x, y, z);

                    int metaIndex = (int) Math.floor(index / 2d);
//                    System.out.println("Reading index " + index + " (meta index " + metaIndex + ", is even? " + (index % 2 == 0) + ")");
                    // TODO fix lighting
                    if (metaIndex % 2 == 0) {
                        meta = (byte) (nbtMetadata[metaIndex] >> 4);
                        bl = (byte) (nbtBlockLight[metaIndex] >> 4);
                        sky = (byte) (nbtSkyLight[metaIndex] >> 4);
                    } else {
                        meta = (byte) (nbtMetadata[metaIndex] & 15);
                        bl = (byte) (nbtBlockLight[metaIndex] & 15);
                        sky = (byte) (nbtSkyLight[metaIndex] & 15);
                    }

                    builder.block(x, y, z, nbtBlocks[index], meta, (byte) 15, (byte) 15);
                }
            }
        }

        // Load entities and add them to chunk WITHOUT spawning them
        chunkNbt.getEntities().forEach(tag -> {
            NBTCompound nbt = (NBTCompound) tag;
            Entity<?> entity = NBTEntity.createNBTEntity(nbt).createEntity(getWorld());
            builder.entity(entity);
        });

        chunkNbt.getTileEntities().forEach(tag -> {
            TileEntity tile = NBTTileEntity.createNBTEntity((NBTCompound) tag).createTileEntity(getWorld());
            builder.tileEntity(tile);
        });

        return true;
    }

    @Override
    public void writeChunk(Chunk chunk) {
        new NBTAlphaChunk(getChunkFile(chunk.getWorld(), chunk.getChunkX(), chunk.getChunkZ()), chunk).save();
    }

    @Override
    public long getSeed() {
        return getLevelDat().getSeed();
    }

    @Override
    public Dimension getDimension() {
        return getLevelDat().getDimension();
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void save() {
        super.save(); // Save level.dat
    }

}
