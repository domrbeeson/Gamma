package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class BlockChangePacketOut extends PacketOut {

    private final int x, y, z;
    private final byte id, metadata;

    public BlockChangePacketOut(int x, int y, int z, byte id, byte metadata) {
        super(Packet.BLOCK_CHANGE);
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
        this.metadata = metadata;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(x);
        stream.write(y);
        stream.writeInt(z);
        stream.write(id);
        stream.write(metadata);
    }

}
