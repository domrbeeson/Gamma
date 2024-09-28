package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class HandshakePacketOut extends PacketOut {

    public HandshakePacketOut() {
        super(Packet.HANDSHAKE);
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        writeString(protocol, stream, "-");
    }
    
}
