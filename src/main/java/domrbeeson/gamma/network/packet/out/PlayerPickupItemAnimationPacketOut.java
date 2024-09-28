package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.entity.ItemEntity;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.player.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerPickupItemAnimationPacketOut extends PacketOut {

    private final int playerEntityId, itemEntityId;

    public PlayerPickupItemAnimationPacketOut(Player player, ItemEntity item) {
        super(Packet.ENTITY_PICK_UP_ITEM);
        playerEntityId = player.getEntityId();
        itemEntityId = item.getEntityId();
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(itemEntityId);
        stream.writeInt(playerEntityId);
    }

}
