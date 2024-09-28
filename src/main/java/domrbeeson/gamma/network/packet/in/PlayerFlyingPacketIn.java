package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerFlyingPacketIn extends WorldPacketIn {

    private boolean flying;

    public PlayerFlyingPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.FLYING, server, connection, stream);

        flying = stream.readBoolean();
    }

    @Override
    public void handle() {

    }

}
