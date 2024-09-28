package domrbeeson.gamma.network.packet.out;

import java.io.DataOutputStream;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

public class KeepAlivePacketOut extends PacketOut {

    public KeepAlivePacketOut() {
        super(Packet.KEEP_ALIVE);
    }

    @Override
    public void send(int protocol, DataOutputStream stream) {
        
    }

}
