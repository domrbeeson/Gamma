package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class WorldPacketIn extends PacketIn {

    protected WorldPacketIn(Packet packet, MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(packet, server, connection, stream);
    }

    @Override
    public void queue() {
        Player player = getServer().getPlayerManager().get(getConnection());
        if (player == null) {
//            System.out.println("PLAYER IS NULL (" + getClass().getSimpleName() + ")");
        } else {
            player.getWorld().queuePacket(this, getConnection());
        }
    }
}
