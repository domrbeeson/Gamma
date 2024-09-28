package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerKickPacketOut extends PacketOut {

    private final @Nullable String message;

    public PlayerKickPacketOut(@Nullable String message) {
        super(Packet.PLAYER_KICK);
        this.message = message;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        writeString(protocol, stream, message != null ? message : "Kicked from server");
    }
    
}
