package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.MouseButton;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class EntityUsePacketIn extends WorldPacketIn {

    private final int playerEntityId, targetEntityId;
    private final MouseButton button;

    public EntityUsePacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.ENTITY_USE, server, connection, stream);

        playerEntityId = getStream().readInt();
        targetEntityId = getStream().readInt();
        button = getStream().readBoolean() ? MouseButton.LEFT : MouseButton.RIGHT;
    }

    @Override
    public void handle() {
        // TODO check whether entity is within 4 block range
    }

}
