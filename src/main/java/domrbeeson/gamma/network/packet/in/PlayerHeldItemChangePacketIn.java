package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerHeldItemChangePacketIn extends WorldPacketIn {

    private final short slot;

    public PlayerHeldItemChangePacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.PLAYER_HELD_ITEM_CHANGE, server, connection, stream);

        slot = stream.readShort();
    }

    @Override
    public void handle() {
        Player player = getServer().getPlayerManager().get(getConnection());
        player.getInventory().setActiveSlot(slot);
    }

}
