package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.event.events.AsyncPlayerJoinEvent;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.out.PlayerKickPacketOut;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;
import domrbeeson.gamma.version.MinecraftVersion;
import domrbeeson.gamma.world.World;

import java.io.DataInputStream;
import java.io.IOException;

public class LoginPacketIn extends ServerPacketIn {

    private static final PlayerKickPacketOut INVALID_PROTOCOL_PACKET = new PlayerKickPacketOut("Invalid protocol");
    private static final int HIGHEST_SUPPORTED_PROTOCOL = MinecraftVersion.values()[MinecraftVersion.values().length - 1].features.protocol();

    private final int protocol;
    private final String username;

    public LoginPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.LOGIN, server, connection, stream);

        protocol = stream.readInt();
        username = readString(protocol, stream);
        stream.readLong();
        stream.readByte();
    }

    @Override
    public void handle() {
        int serverProtocol = MinecraftServer.SERVER_SETTINGS.getMinecraftVersion().features.protocol();
        if (protocol < serverProtocol || protocol > HIGHEST_SUPPORTED_PROTOCOL) {
            getConnection().getWriter().send(protocol, new PlayerKickPacketOut("Unsupported client!"));
            return;
        }

        MinecraftVersion version = MinecraftVersion.getVersion(protocol);
        if (version == null) {
            getConnection().getWriter().send(protocol, INVALID_PROTOCOL_PACKET);
            return;
        }

        Player existingPlayer = getServer().getPlayerManager().get(getConnection());
        if (existingPlayer != null) {
            System.out.println(username + " logged in from another location");
            existingPlayer.kick("Logged in from another location!");
        }

        World world = getServer().getWorldManager().getDefaultWorld();
        AsyncPlayerJoinEvent event = new AsyncPlayerJoinEvent(version, username, world);
        getServer().call(event);
        if (event.isCancelled()) {
            getConnection().getWriter().send(protocol, new PlayerKickPacketOut(event.getKickMessage()));
            return;
        }

        getServer().getPlayerManager().create(getConnection(), username, version, event.getJoinMessage());
    }

}
