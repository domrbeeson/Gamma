package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class AsyncPacketIn extends PacketIn {

    protected AsyncPacketIn(Packet packet, MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(packet, server, connection, stream);
    }

    @Override
    public void queue() {
        handle();
    }
}
