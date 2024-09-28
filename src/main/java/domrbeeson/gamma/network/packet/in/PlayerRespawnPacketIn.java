package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerRespawnPacketIn extends ServerPacketIn {

    public PlayerRespawnPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.PLAYER_RESPAWN, server, connection, stream);

        // TODO if player isn't dead, ignore this packet
    }

    @Override
    public void handle() {
        // TODO this is a server packet because player might respawn in a different world
    }

}
