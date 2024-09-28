package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.world.Chunk;

import java.io.DataOutputStream;
import java.io.IOException;

public class MultiBlockChangePacketOut extends PacketOut {

    protected final int chunkX, chunkZ;
    protected final short blocksChanged;
    protected final short[] coordinates;
    protected final byte[] blocks, blockData;

    public MultiBlockChangePacketOut(Chunk chunk, byte xMin, int yMin, byte zMin, byte xMax, int yMax, byte zMax) {
        super(Packet.MULTI_BLOCK_CHANGE);

        chunkX = chunk.getChunkX();
        chunkZ = chunk.getChunkZ();
        blocksChanged = (short)((xMax - xMin) * (yMax - yMin) * (zMax - zMin));
        coordinates = new short[blocksChanged];
        blocks = new byte[blocksChanged];
        blockData = new byte[blocksChanged];

        int i = 0;
        for (byte x = xMin; x < xMax; x++) {
            for (int y = yMin; y < yMax; y++) {
                for (byte z = zMin; z < zMax; z++) {
                    coordinates[i] = (short)((x & 15) << 12 | (z & 15) << 8 | y);
                    blocks[i] = chunk.getBlockId(x, y, z);
                    blockData[i] = chunk.getBlockMetadata(x, y, z);
                    i++;
                }
            }
        }
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(chunkX);
        stream.writeInt(chunkZ);
        stream.writeShort(blocksChanged);
        for (short coordinate : coordinates) {
            stream.writeShort(coordinate);
        }
        stream.write(blocks);
        stream.write(blockData);
    }

}
