package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class ServerPacketIn extends PacketIn {

    protected ServerPacketIn(Packet packet, MinecraftServer server, PlayerConnection connecetion, DataInputStream stream) throws IOException {
        super(packet, server, connecetion, stream);
    }

    @Override
    public void queue() {
        getServer().queuePacket(this, getConnection());
    }
}
