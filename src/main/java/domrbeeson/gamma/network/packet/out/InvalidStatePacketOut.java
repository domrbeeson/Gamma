package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class InvalidStatePacketOut extends PacketOut {

    private final byte reason;

    public InvalidStatePacketOut(Reason reason) {
        super(Packet.INVALID_STATE);
        this.reason = (byte) reason.ordinal();
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeByte(reason);
    }

    public enum Reason {
        INVALID_BED,
        BEGIN_RAINING,
        STOP_RAINING,
        ;
    }

}
