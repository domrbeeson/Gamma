package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class KeepAlivePacketIn extends AsyncPacketIn {

    protected KeepAlivePacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.KEEP_ALIVE, server, connection, stream);
    }

    @Override
    public void handle() {

    }

}
