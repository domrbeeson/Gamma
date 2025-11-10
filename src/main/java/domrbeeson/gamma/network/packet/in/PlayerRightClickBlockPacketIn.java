package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.BlockHandlers;
import domrbeeson.gamma.block.handler.BlockHandler;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.Direction;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerRightClickBlockPacketIn extends WorldPacketIn {

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
        // TODO validate clicked block is in range and where player is looking

        if (!Direction.isInRange(this.direction)) {
            // Player clicked something out of range; cannot rely on this for distance checks because it's client-side but need to support it anyway
            return;
        }
        Direction direction = Direction.getById(this.direction);

        Player player = getServer().getPlayerManager().get(getConnection());
        Chunk chunk = player.getWorld().getLoadedChunk(clickedX >> 4, clickedZ >> 4);
        if (chunk == null) {
            return;
        }

        Item heldItem = player.getInventory().getHeldItem();
        if (heldItem.getMaterial() != Material.AIR && heldItem.getMaterial().block) {
            int finalX = clickedX;
            byte finalY = clickedY;
            int finalZ = clickedZ;

            BlockHandler clickedBlockHandler = BlockHandlers.getBlockHandler(chunk.getBlockId(clickedX, clickedY, clickedZ));
            if (clickedBlockHandler.isSolid()) { // TODO is this just solid blocks?
                Pos adjusted = direction.applyDirection(finalX, finalY, finalZ);
                finalX = adjusted.getBlockX();
                finalY = (byte) adjusted.getBlockY(); // TODO will this cause problem with height limit?
                finalZ = adjusted.getBlockZ();

                chunk = player.getWorld().getLoadedChunk(finalX >> 4, finalZ >> 4);
                if (chunk == null) {
                    return;
                }
            }

            short heldId = heldItem.getId();
            short heldMetadata = heldItem.getMetadata();
            boolean placed = chunk.placeBlockAsPlayer(player, finalX, finalY, finalZ, Material.get(heldId, heldItem.getMetadata()).blockId, (byte) heldMetadata, clickedX, clickedY, clickedZ);
            if (placed) {
                player.getInventory().setHeldItem(Material.get(heldId, heldMetadata).getItem(heldItem.getAmount() - 1));
            }
        } else {
            chunk.rightClickAsPlayer(player, clickedX, clickedY, clickedZ, direction);
        }
    }

}
