package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.Direction;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerRightClickBlockPacketIn extends WorldPacketIn {

    private static final double MAX_CLICK_DISTANCE = 5;

    private final int clickedX, clickedZ;
    private final byte clickedY, direction;

    public PlayerRightClickBlockPacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.PLAYER_BLOCK_PLACE, server, connection, stream);

        clickedX = stream.readInt();
        clickedY = stream.readByte();
        clickedZ = stream.readInt();
        direction = stream.readByte();

        // Read held item data, but don't rely on it because the client can easily spoof this
        if (stream.readShort() > 0) { // Item ID
            stream.readByte(); // Amount
            stream.readShort(); // Metadata
        }
    }

    @Override
    public void handle() {
        // TODO validate clicked block is where player is looking (maybe ray cast? would have to account for buttons, or just say clicking the block is good enough)

        if (!Direction.isInRange(direction)) {
            return;
        }

        Player player = getServer().getPlayerManager().get(getConnection());
        if (player == null) {
            return;
        }

        Item heldItem = player.getInventory().getHeldItem();
        Chunk chunk = player.getWorld().getLoadedChunk(clickedX >> 4, clickedZ >> 4);
        Pos clickedPos = player.getPos().distance(clickedX, clickedY, clickedZ) <= MAX_CLICK_DISTANCE ? new Pos(clickedX, clickedY, clickedZ) : null;
        if (clickedPos == null) {
            if (!heldItem.isAir()) {
                player.getInventory().rightClickHeldItem();
            }
            return;
        }

        if (chunk != null) {
            Direction direction = Direction.getById(this.direction);
            chunk.rightClickAsPlayer(player, clickedX, clickedY, clickedZ, direction);
        }

//        if ((heldItem != null && heldItem.getMaterial() != Material.AIR) && heldItem.getMaterial().block) {
//            int finalX = clickedX;
//            byte finalY = clickedY;
//            int finalZ = clickedZ;
//
//            BlockHandler clickedBlockHandler = BlockHandlers.getBlockHandler(chunk.getBlockId(clickedX, clickedY, clickedZ));
//            if (clickedBlockHandler.isSolid()) { // TODO is this just solid blocks?
//                Pos adjusted = direction.applyDirection(finalX, finalY, finalZ);
//                finalX = adjusted.getBlockX();
//                finalY = (byte) adjusted.getBlockY(); // TODO will this cause problem with height limit?
//                finalZ = adjusted.getBlockZ();
//
//                chunk = player.getWorld().getLoadedChunk(finalX >> 4, finalZ >> 4);
//                if (chunk == null) {
//                    return;
//                }
//            }
//
//            short heldId = heldItem.getId();
//            short heldMetadata = heldItem.getMetadata();
//            boolean placed = chunk.placeBlockAsPlayer(player, finalX, finalY, finalZ, Material.get(heldId, heldItem.getMetadata()).blockId, (byte) heldMetadata, clickedX, clickedY, clickedZ);
//            if (placed) {
//                heldItem.setAmount(heldItem.getAmount() - 1);
////                player.getInventory().setHeldItem(Material.get(heldId, heldMetadata).getItem(heldItem.getAmount() - 1));
//            }
//        } else {
//            chunk.rightClickAsPlayer(player, clickedX, clickedY, clickedZ, direction);
//        }
    }
}
