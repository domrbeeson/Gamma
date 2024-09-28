package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.world.Dimension;

import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerRespawnPacketOut extends PacketOut {

    private final Dimension dimension;

    public PlayerRespawnPacketOut(Dimension dimension) {
        super(Packet.PLAYER_RESPAWN);
        this.dimension = dimension;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        if (protocol >= 12) {
            stream.writeByte(dimension.id);
        }
    }
}
