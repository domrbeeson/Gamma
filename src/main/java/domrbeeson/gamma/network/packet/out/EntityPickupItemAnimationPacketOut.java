package domrbeeson.gamma.network.packet.out;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.ItemEntity;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

import java.io.DataOutputStream;
import java.io.IOException;

public class EntityPickupItemAnimationPacketOut extends PacketOut {

    private final int itemEntityId;
    private final int holderEntityId;

    public EntityPickupItemAnimationPacketOut(ItemEntity item, Entity holder) {
        super(Packet.ENTITY_PICK_UP_ITEM);
        this.itemEntityId = item.getEntityId();
        this.holderEntityId = holder.getEntityId();
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(itemEntityId);
        stream.writeInt(holderEntityId);
    }

}
