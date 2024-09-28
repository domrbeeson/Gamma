package domrbeeson.gamma.network.packet;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class PacketOut {

    private final Packet packet;

    public PacketOut(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public abstract void send(int protocol, DataOutputStream stream) throws IOException;

    protected final void writeString(int protocol, DataOutputStream stream, String data) throws IOException {
        if (protocol < 11) {
            stream.writeUTF(data);
        } else {
            stream.writeShort(data.length());
            stream.writeChars(data);
        }
    }

}
