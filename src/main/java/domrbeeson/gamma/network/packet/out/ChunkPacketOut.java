package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.format.NotchianWorldFormat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

public class ChunkPacketOut extends PacketOut {

    private static final int DATA_SIZE = (int) Math.round(Chunk.WIDTH * Chunk.HEIGHT * Chunk.WIDTH * 2.5);

    private final int blockStartX, blockStartZ;
    private final byte[] compressedChunkData;
    private final int compressedDataSize;

    private final Chunk chunk;

    public ChunkPacketOut(Chunk chunk, int compressionLevel) {
        super(Packet.CHUNK);
        this.chunk = chunk;

        blockStartX = chunk.getChunkX() * Chunk.WIDTH;
        blockStartZ = chunk.getChunkZ() * Chunk.WIDTH;

        byte[] chunkData = new byte[DATA_SIZE];
        int index;
        byte id, metadata, skyLight, blockLight;
        for (byte x = 0; x < Chunk.WIDTH; x++) {
            for (int y = 0; y < Chunk.HEIGHT; y++) {
                for (byte z = 0; z < Chunk.WIDTH; z++) {
                    index = NotchianWorldFormat.getBlockIndex(x, y, z);
                    id = chunk.getBlockId(x, y, z);
                    metadata = chunk.getBlockMetadata(x, y, z);
                    blockLight = chunk.getBlockLight(x, y, z);
                    skyLight = chunk.getSkyLight(x, y, z);

                    // TODO this needs fixing
                    int halfIndex = (int) Math.floor(index / 2d);
                    if (halfIndex % 2 == 0) {
                        chunkData[32768 + halfIndex] = setNibble(metadata, chunkData[32768 + halfIndex]);
                        chunkData[49152 + halfIndex] = setNibble(blockLight, chunkData[49152 + halfIndex]);
                        chunkData[65536 + halfIndex] = setNibble(skyLight, chunkData[65536 + halfIndex]);
                    } else {
                        chunkData[32768 + halfIndex] = setNibble(chunkData[32768 + halfIndex], metadata);
                        chunkData[49152 + halfIndex] = setNibble(chunkData[49152 + halfIndex], blockLight);
                        chunkData[65536 + halfIndex] = setNibble(chunkData[65536 + halfIndex], skyLight);
                    }
                    chunkData[index] = id;
                }
            }
        }

        Deflater deflater = new Deflater(compressionLevel);
        try {
            deflater.setInput(chunkData);
            deflater.finish();
            compressedChunkData = new byte[chunkData.length];
            compressedDataSize = deflater.deflate(compressedChunkData);
        } finally {
            deflater.end();
        }
    }

    private byte setNibble(byte upper, byte lower) {
        return (byte)(lower << 4 | (upper & 15));
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(blockStartX);
        stream.writeShort(0);
        stream.writeInt(blockStartZ);
        stream.write(Chunk.WIDTH - 1);
        stream.write(Chunk.HEIGHT - 1);
        stream.write(Chunk.WIDTH - 1);
        stream.writeInt(compressedDataSize);
        stream.write(compressedChunkData, 0, compressedDataSize);
    }

    public Chunk getChunk() {
        return chunk;
    }

}
