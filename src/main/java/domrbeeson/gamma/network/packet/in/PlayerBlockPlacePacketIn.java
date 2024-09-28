package domrbeeson.gamma.network.packet.in;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.handler.BlockHandler;
import domrbeeson.gamma.event.events.PlayerRightClickBlockEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerBlockPlacePacketIn extends WorldPacketIn {

    private final int clickedX, clickedZ;
    private final byte clickedY, direction;

    public PlayerBlockPlacePacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
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

        Player player = getServer().getPlayerManager().get(getConnection());
        Item heldItem = player.getInventory().getHeldItem();

        PlayerRightClickBlockEvent rightClickEvent = new PlayerRightClickBlockEvent(player, clickedX, clickedY, clickedZ, heldItem);
        rightClickEvent.call(player.getWorld());
        if (rightClickEvent.isCancelled()) {
            return;
        }

        Block clickedBlock = player.getWorld().getChunk(clickedX >> 4, clickedZ >> 4).join().getBlock(clickedX, clickedY, clickedZ);
        BlockHandler clickedBlockHandler = getServer().getBlockHandlers().get(clickedBlock.id());
        if (clickedBlockHandler.onRightClick(getServer(), clickedBlock, player)) {
            return;
        }

        short heldId = heldItem.id();
        if (heldId == 0) {
            return;
        }

        int placedX = clickedX;
        byte placedY = clickedY;
        int placedZ = clickedZ;
        if (clickedBlockHandler.isSolid()) {
            switch (direction) {
                case 0 -> placedY -= 1;
                case 1 -> placedY += 1;
                case 2 -> placedZ -= 1;
                case 3 -> placedZ += 1;
                case 4 -> placedX -= 1;
                case 5 -> placedX += 1;
            }
        }
        short metadata = heldItem.metadata();
        player.getInventory().setHeldItem(Material.get(heldId, metadata).getItem(heldItem.amount() - 1));
        player.getWorld().getChunk(placedX >> 4, placedZ >> 4).join().placeAsPlayer(player, placedX, placedY, placedZ, Material.get(heldId, heldItem.metadata()).blockId, (byte) metadata);
    }

}
