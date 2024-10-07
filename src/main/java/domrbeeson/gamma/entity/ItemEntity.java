package domrbeeson.gamma.entity;

import domrbeeson.gamma.entity.metadata.EntityMetadata;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.network.packet.out.ItemSpawnPacketOut;
import domrbeeson.gamma.network.packet.out.PlayerPickupItemAnimationPacketOut;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemEntity extends HealthEntity<EntityMetadata> {

    public static final String NAME = "Item";

    private final static long TICKS_BEFORE_PICKUP = 5;
    private final static int TICKS_BEFORE_DESPAWN = 6_000; // 5 minutes

    private final Item item;

    private @Nullable ItemSpawnPacketOut spawnPacket = null;

    public ItemEntity(World world, Pos pos, Item item) {
        super(EntityType.ITEM, world, pos, new EntityMetadata(), new CollisionBox(pos, 0.25, 0.25), (short) 5);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public void tick(long ticks) {
        long time = getWorld().getTime();
        if (time >= getSpawnTime() + TICKS_BEFORE_PICKUP) {
            Chunk chunk = getWorld().getLoadedChunk(getPos().getChunkX(), getPos().getChunkZ());
            for (Player player : chunk.getViewers()) { // TODO check adjacent chunk if item is near a chunk border
                if (getCollisionBox().collides(player.getCollisionBox())) {
                    player.getInventory().addItem(item);
                    player.sendPacket(new PlayerPickupItemAnimationPacketOut(player, this));
                    remove();
                    break;
                }
            }
        } else if (ticks - getSpawnTime() >= TICKS_BEFORE_DESPAWN) {
            remove();
        }

        super.tick(ticks);
    }

    @Override
    public PacketOut getSpawnPacket() {
        if (spawnPacket == null) {
            spawnPacket = new ItemSpawnPacketOut(this);
        }
        return spawnPacket;
    }

}
