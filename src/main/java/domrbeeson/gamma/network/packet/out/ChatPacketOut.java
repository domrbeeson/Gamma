package domrbeeson.gamma.network.packet.out;

import java.io.DataOutputStream;
import java.io.IOException;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

public class ChatPacketOut extends PacketOut {

    private final String message;

    public ChatPacketOut(String message) {
        super(Packet.CHAT);
        this.message = message;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        writeString(protocol, stream, message);
    }
    
}
