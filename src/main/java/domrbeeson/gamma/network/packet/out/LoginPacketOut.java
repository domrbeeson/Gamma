package domrbeeson.gamma.network.packet.out;

import java.io.DataOutputStream;
import java.io.IOException;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.world.Dimension;

public class LoginPacketOut extends PacketOut {

    private final int entityId;
    private final Dimension dimension;

    public LoginPacketOut(int entityId, Dimension dimension) {
        super(Packet.LOGIN);
        this.entityId = entityId;
        this.dimension = dimension;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        if (protocol < 11) {
            stream.writeUTF("");
            stream.writeUTF("");
        } else {
            writeString(protocol, stream, "");
        }
        stream.writeLong(0);
        stream.writeByte(dimension.id);
    }
    
}
