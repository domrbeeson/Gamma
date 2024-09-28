package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class WindowClosePacketIn extends WorldPacketIn {

    private final byte inventoryId;

    public WindowClosePacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.CLOSE_WINDOW, server, connection, stream);

        inventoryId = stream.readByte();
    }

    @Override
    public void handle() {
        Player player = getServer().getPlayerManager().get(getConnection());
        player.closeInventory();
    }

}
