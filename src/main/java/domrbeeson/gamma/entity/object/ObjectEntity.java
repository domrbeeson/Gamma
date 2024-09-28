package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.EntityMetadata;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.network.packet.out.ObjectSpawnPacketOut;
import domrbeeson.gamma.world.World;

public abstract class ObjectEntity extends Entity<EntityMetadata> {

    public ObjectEntity(EntityType type, World world, Pos pos, CollisionBox collisionBox) {
        super(type, world, pos, new EntityMetadata(), collisionBox);
    }

    @Override
    public PacketOut getSpawnPacket() {
        return new ObjectSpawnPacketOut(this);
    }

    @Override
    public void tick(long ticks) {
        super.tick(ticks);
    }
}
