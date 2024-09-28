package domrbeeson.gamma.entity;

import domrbeeson.gamma.Removable;
import domrbeeson.gamma.Tickable;
import domrbeeson.gamma.Viewable;
import domrbeeson.gamma.entity.metadata.EntityMetadata;
import domrbeeson.gamma.event.events.CancellableEvent;
import domrbeeson.gamma.event.events.EntityMoveChunkEvent;
import domrbeeson.gamma.event.events.EntityMoveEvent;
import domrbeeson.gamma.event.events.EntityTeleportEvent;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.network.packet.out.*;
import domrbeeson.gamma.player.EntityInRange;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class Entity<T extends EntityMetadata> implements Tickable, Removable, Viewable {

    public static final double CHUNK_VIEW_RANGE = 4;

    protected static final CollisionBox EMPTY_COLLISION_BOX = new CollisionBox(Pos.ZERO, 0, 0);

    private static final Set<Integer> ENTITY_IDS = new HashSet<>();

    private static int MIN_ID = 1;
    private static int getNewEntityId() {
        for (int i = MIN_ID; i < Integer.MAX_VALUE; i++) {
            if (!ENTITY_IDS.contains(i)) {
                ENTITY_IDS.add(i);
                MIN_ID = i + 1;
                return i;
            }
        }
        return -1;
    }

    private final int id = getNewEntityId();
    private final List<Player> viewers = new ArrayList<>();
    private final EntityDestroyPacketOut destroyPacket;
    private final long spawnTime;
    private final EntityType type;
    private final T metadata;
    private final CollisionBox collisionBox;

    private World world;
    private Pos pos;
    private boolean onGround = false;
    private boolean spawned = false;
    private boolean removed = false;
    private @Nullable World nextTickWorld = null;
    private @Nullable Pos nextTickPos = null;
    private boolean teleported = false;
    private double health;

    public Entity(EntityType type, World world, Pos pos, T metadata, CollisionBox collisionBox) {
        this.type = type;
        this.world = world;
        this.pos = pos;
        destroyPacket = new EntityDestroyPacketOut(id);
        spawnTime = world.getTime();
        this.metadata = metadata;
        this.collisionBox = collisionBox;
    }

    public final int getEntityId() {
        return id;
    }

    public final EntityType getType() {
        return type;
    }

    public final World getWorld() {
        return world;
    }

    protected final void updateWorld(World world) {
        if (nextTickWorld != null || world == null || this.world == world) {
            return;
        }
        nextTickWorld = world;
        teleported = true;
        if (nextTickPos == null) { // nextTickPos needs to be set for world to update in the tick method
            nextTickPos = pos;
        }
    }

    public final Pos getPos() {
        return pos;
    }

    public void updatePos(Pos pos) {
        if (nextTickPos != null) {
            return;
        }
        nextTickPos = pos;
        teleported = false;
    }

    public final void teleport(World world, Pos pos) {
        nextTickWorld = world;
        nextTickPos = pos;
        teleported = true;
    }

    public final boolean isOnGround() {
        return onGround;
    }

    public final boolean hasSpawned() {
        return world != null && pos != null;
    }

    public final long getSpawnTime() {
        return spawnTime;
    }

    public final T getMetadata() {
        return metadata;
    }

    public final CollisionBox getCollisionBox() {
        return collisionBox;
    }

    public void spawn() {
        if (spawned) {
            return;
        }
        spawned = true;
        world.getChunk(pos.getChunkX(), pos.getChunkZ()).join().addEntity(this);
        for (EntityInRange playerInRange : world.getViewersInRange(pos, CHUNK_VIEW_RANGE * Chunk.WIDTH)) {
            if (this instanceof Player && playerInRange.entity() == this) {
                continue;
            }
            addViewer((Player) playerInRange.entity());
        }
    }

    public boolean isSpawned() {
        return spawned;
    }

    @Override
    public void remove() {
        if (removed) {
            return;
        }
        removed = true;
    }

    @Override
    public boolean isRemoved() {
        return removed && !spawned;
    }

    @Override
    public boolean isViewing(Player player) {
        return viewers.contains(player);
    }

    @Override
    public List<Player> getViewers() {
        return viewers;
    }

    @Override
    public CompletableFuture<Void> addViewer(Player player) {
        if (player != this && !isViewing(player)) {
            viewers.add(player);
            player.sendPacket(getSpawnPacket());
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> removeViewer(Player player) {
        if (player != this && viewers.remove(player)) {
//            System.out.println("Removing viewer '" + player.getUsername() + "' from " + (this instanceof Player ? ((Player) this).getUsername() : getClass().getSimpleName()));
//            for (var thing : Thread.currentThread().getStackTrace()) {
//                System.out.println(thing);
//            }
            player.sendPacket(destroyPacket);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public boolean hasViewers() {
        return !viewers.isEmpty();
    }

    public abstract PacketOut getSpawnPacket();

    public PacketOut getMovePacket(Pos currentPos, Pos newPos) {
        Pos.EncodedPos encodedCurrentPos = currentPos.encode();
        Pos.EncodedPos encodedNewPos = newPos.encode();

        int deltaX = encodedNewPos.x() - encodedCurrentPos.x();
        int deltaY = encodedNewPos.y() - encodedCurrentPos.y();
        int deltaZ = encodedNewPos.z() - encodedCurrentPos.z();

        boolean teleported = deltaX > Byte.MAX_VALUE || deltaX < Byte.MIN_VALUE
                || deltaY > Byte.MAX_VALUE || deltaY < Byte.MIN_VALUE
                || deltaZ > Byte.MAX_VALUE || deltaZ < Byte.MIN_VALUE;

        PacketOut packet;
        boolean posChanged;
        if (teleported) {
            packet = new EntityTeleportPacketOut(this, newPos);
        } else {
            Pos.EncodedPos deltaPos = new Pos.EncodedPos(deltaX, deltaY, deltaZ, (byte) 1, (byte) 1);
            if ((posChanged = currentPos.x() != newPos.x() || currentPos.y() != newPos.y() || currentPos.z() != newPos.z()) && currentPos.yaw() != newPos.yaw() || currentPos.pitch() != newPos.pitch()) {
                packet = new EntityRelativeMoveAndLookPacketOut(this, deltaPos);
            } else if (posChanged) {
                packet = new EntityRelativeMovePacketOut(this, deltaPos);
            } else {
                packet = new EntityLookPacketOut(this, deltaPos);
            }
        }
        return packet;
    }

    public SortedSet<EntityInRange> getViewersInRange() {
        return getViewersInRange(getPos(), CHUNK_VIEW_RANGE * Chunk.WIDTH);
    }
    
    @Override
    public void tick(long ticks) {
        if (isRemoved()) {
            return;
        }

        if (spawned && removed) {
            spawned = false;
            ENTITY_IDS.remove(id);
            if (MIN_ID > id) {
                MIN_ID = id;
            }
            world.getChunk(pos.getChunkX(), pos.getChunkZ()).join().removeEntity(this);
            removeAllViewers();
            return;
        }

        if (metadata.hasChanged()) {
            EntityMetadataPacketOut packet = new EntityMetadataPacketOut(this);
            viewers.forEach(viewer -> viewer.sendPacket(packet));
        }

        if (nextTickPos != null) {
            CancellableEvent moveEvent;
            if (teleported) {
                moveEvent = new EntityTeleportEvent(this, world, pos, nextTickWorld != null ? nextTickWorld : world, nextTickPos);
            } else {
                moveEvent = new EntityMoveEvent(this, pos, nextTickPos);
            }
            if (!moveEvent.isCancelled()) {
                collisionBox.setCentre(nextTickPos);

                int oldChunkX = pos.getChunkX();
                int oldChunkZ = pos.getChunkZ();
                int newChunkX = nextTickPos.getChunkX();
                int newChunkZ = nextTickPos.getChunkZ();
                if (nextTickWorld != null || oldChunkX != newChunkX || oldChunkZ != newChunkZ) {
                    Chunk oldChunk = world.getChunk(oldChunkX, oldChunkZ).join();
                    Chunk newChunk = world.getChunk(newChunkX, newChunkZ).join();
                    EntityMoveChunkEvent moveChunkEvent = new EntityMoveChunkEvent(this, oldChunk, newChunk);
                    world.call(moveChunkEvent);

                    if (oldChunk.removeEntity(this)) {
                        onChunkChange(oldChunk, newChunk);
                    }
                    newChunk.addEntity(this);

                }

                PacketOut movePacket = getMovePacket(pos, nextTickPos);
                viewers.forEach(viewer -> viewer.sendPacket(movePacket));

                if (nextTickWorld != null) {
                    world = nextTickWorld;
                    nextTickWorld = null;
                }

                onMove(pos, nextTickPos, teleported);
            } else {
                onMoveCancel();
            }
            pos = nextTickPos;
            nextTickPos = null;
        }
    }

    protected void onMove(Pos oldPos, Pos newPos, boolean teleported) {

    }

    protected void onChunkChange(Chunk oldChunk, Chunk newChunk) {

    }

    protected void onMoveCancel() {

    }

    public final boolean isWithinChunkViewDistance(Pos pos) {
        return isWithinChunkViewDistance(pos.getChunkX(), pos.getChunkZ());
    }

    public boolean isWithinChunkViewDistance(int chunkX, int chunkZ) {
        // TODO this doesn't work, it makes players despawn and respawn when moving between chunk boundaries
        int thisChunkX = pos.getChunkX();
        int thisChunkZ = pos.getChunkZ();
        return thisChunkX <= chunkX + World.ENTITY_VIEW_DISTANCE_CHUNKS && thisChunkX >= chunkX - World.ENTITY_VIEW_DISTANCE_CHUNKS
                && thisChunkZ <= chunkZ + World.ENTITY_VIEW_DISTANCE_CHUNKS && thisChunkZ >= chunkZ + World.ENTITY_VIEW_DISTANCE_CHUNKS;
    }

}
