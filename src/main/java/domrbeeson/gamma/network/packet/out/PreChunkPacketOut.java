package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class PreChunkPacketOut extends PacketOut {

    private final int x, z;
    private final boolean initialise;

    public PreChunkPacketOut(int x, int z, boolean initialise) {
        super(Packet.PRE_CHUNK);
        this.x = x;
        this.z = z;
        this.initialise = initialise;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(x);
        stream.writeInt(z);
        stream.writeBoolean(initialise);
    }

    public int x() {
        return x;
    }

    public int z() {
        return z;
    }

    public boolean load() {
        return initialise;
    }

}
