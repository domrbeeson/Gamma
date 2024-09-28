package domrbeeson.gamma.network.packet.out;

import java.io.DataOutputStream;
import java.io.IOException;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

public class TimePacketOut extends PacketOut {

    private final long ticks;

    public TimePacketOut(long ticks) {
        super(Packet.TIME);
        this.ticks = ticks;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeLong(ticks);
    }
    
}
