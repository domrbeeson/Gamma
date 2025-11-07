package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.BlockHandlers;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;
import domrbeeson.gamma.world.Chunk;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerDiggingPacketIn extends WorldPacketIn {

    private static final double BLOCK_BREAK_RANGE = 4;

    private final Status status;
    private final int x, y, z;
    private final byte face;

    public PlayerDiggingPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.PLAYER_DIGGING, server, connection, stream);

        switch (stream.readByte()) {
            case 0 -> status = Status.STARTED_DIGGING;
            case 2 -> status = Status.FINISHED_DIGGING;
            case 4 -> status = Status.DROP_ITEM; // TODO
            default -> status = Status.UNKNOWN;
        }
        x = stream.readInt();
        y = stream.readByte();
        z = stream.readInt();
        face = stream.readByte();
    }

    @Override
    public void handle() {
        if (status == Status.DROP_ITEM) {
            return;
        }

        Player player = getServer().getPlayerManager().get(getConnection());
        // TODO check if player is in range of block
//        if (player.getPos().distance(new Pos(x + 0.5, y + 0.5, z + 0.5)) > BLOCK_BREAK_RANGE) {
//            return;
//        }

        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        Chunk chunk = player.getWorld().getLoadedChunk(chunkX, chunkZ);
        if (chunk == null) {
            return;
        }

        Block block = player.getWorld().getBlock(x, y, z);
        if (block == null || block.id() == 0) {
            return;
        }

        if (status == Status.STARTED_DIGGING) {
            BlockHandlers.getBlockHandler(block.id()).onLeftClick(getServer(), block, player);
        } else if (status == Status.FINISHED_DIGGING) {
            chunk.breakBlockAsPlayer(player, x, y, z);
        }
    }

    public enum Status {
        STARTED_DIGGING,
        FINISHED_DIGGING,
        DROP_ITEM,
        UNKNOWN,
        ;
    }
    
}
