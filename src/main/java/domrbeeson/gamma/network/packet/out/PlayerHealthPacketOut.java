package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerHealthPacketOut extends PacketOut {

    private final short health;

    public PlayerHealthPacketOut(short health) {
        super(Packet.PLAYER_HEALTH);
        this.health = health;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeShort(health);
    }

}
