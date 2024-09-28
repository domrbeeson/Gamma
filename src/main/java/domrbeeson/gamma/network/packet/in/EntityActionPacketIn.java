package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class EntityActionPacketIn extends WorldPacketIn {

    private final int entityId;
    private final EntityAction action;

    public EntityActionPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.ENTITY_ACTION, server, connection, stream);
        entityId = stream.readInt();
        action = EntityAction.values()[stream.readByte()];
    }

    @Override
    public void handle() {
        // TODO
    }

    private enum EntityAction {
        CROUCH,
        UNCROUCH,
        LEAVE_BED,
        ;

        public final byte id = (byte) (ordinal() + 1);

    }

}
