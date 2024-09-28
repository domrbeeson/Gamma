package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.block.Instrument;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class NoteBlockPacketOut extends PacketOut {

    private final int x, z;
    private final short y;
    private final byte instrument, pitch;

    public NoteBlockPacketOut(int x, short y, int z, Instrument instrument, byte pitch) {
        super(Packet.NOTE_BLOCK);
        this.x = x;
        this.y = y;
        this.z = z;
        this.instrument = (byte) instrument.ordinal();
        if (pitch > 24) {
            pitch = 24;
        } else if (pitch < 0) {
            pitch = 0;
        }
        this.pitch = pitch;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(x);
        stream.writeShort(y);
        stream.writeInt(z);
        stream.writeByte(instrument);
        stream.writeByte(pitch);
    }

}
