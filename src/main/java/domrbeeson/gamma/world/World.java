package domrbeeson.gamma.world;

import domrbeeson.gamma.*;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.event.EventGroup;
import domrbeeson.gamma.event.RegisteredEventListener;
import domrbeeson.gamma.event.events.player.PlayerMoveEvent;
import domrbeeson.gamma.event.events.server.WorldUnloadEvent;
import domrbeeson.gamma.inventory.Inventory;
import domrbeeson.gamma.inventory.PlayerInventory;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.network.packet.out.EntityTeleportPacketOut;
import domrbeeson.gamma.network.packet.out.LoginPacketOut;
import domrbeeson.gamma.network.packet.out.PlayerRespawnPacketOut;
import domrbeeson.gamma.network.packet.out.TimePacketOut;
import domrbeeson.gamma.player.EntityInRange;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.task.ScheduledTask;
import domrbeeson.gamma.task.Scheduler;
import domrbeeson.gamma.world.format.InvalidWorldFormatException;
import domrbeeson.gamma.world.format.WorldFormat;
import domrbeeson.gamma.world.terrain.TerrainGenerator;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class World extends EventGroup<Event.WorldEvent> implements Tickable, Unloadable, Viewable, Saveable {

    public static final int MAXIMUM_CHUNK_RADIUS = 15;
    public static final int INITIAL_CHUNK_RADIUS = 7;
    public static final int MINIMUM_CHUNK_RADIUS = 3;
    public static final int ENTITY_VIEW_DISTANCE_CHUNKS = 3;

    private final MinecraftServer server;
    private final Scheduler scheduler = new Scheduler();
    private final WorldManager manager;
    private final List<Player> viewers = new ArrayList<>();
    private final Map<Long, Chunk> loadedChunks = new Long2ObjectArrayMap<>();
    private final String name;
    private final WorldFormat format;
    private final TerrainGenerator generator;
    private final long worldTime;
    private final RegisteredEventListener<PlayerMoveEvent> playerMoveListener;
    private final Set<Chunk> saveChunks = new HashSet<>();
    private final Set<Inventory> inventories = new HashSet<>();

    private int viewDistance;
    private long time;

    protected World(MinecraftServer server, WorldManager manager, String name, WorldFormat format, TerrainGenerator generator) throws InvalidWorldFormatException {
        this.server = server;
        this.manager = manager;
        this.name = name;
        this.format = format;
        this.generator = generator;
        this.viewDistance = Math.max(MINIMUM_CHUNK_RADIUS, MinecraftServer.SERVER_SETTINGS.getViewDistance());

        format.load(this);

        this.worldTime = 0; // TODO load from world format
        this.time = 0;

        scheduler.scheduleTask(new TimeUpdaterTask(this));

        playerMoveListener = listen(PlayerMoveEvent.class, event -> {
            viewers.forEach(viewer -> {
                if (viewer == event.getPlayer()) {
                    return;
                }
                viewer.sendPacket(new EntityTeleportPacketOut(event.getPlayer(), event.getNewPos()));
            });
        });

        System.out.println("Loaded world '" + name + "' [Format: " + format.getClass().getSimpleName() + ", Generator: " + generator.getClass().getSimpleName() + ", View distance: " + viewDistance + ", Seed: " + format.getSeed() + "]");
    }

    @Override
    public void unload() {
        WorldUnloadEvent event = new WorldUnloadEvent(this);
        server.call(event);
        List<Player> viewers = new ArrayList<>(this.viewers);
        viewers.forEach(player -> player.kick("World unloaded"));
        manager.removeWorld(this);
        playerMoveListener.stop();

        format.save();
        for (Chunk chunk : new HashSet<>(loadedChunks.values())) {
            chunk.unload();
        }
    }

    protected boolean removeChunk(Chunk chunk) {
        if (loadedChunks.remove(chunk.getChunkIndex()) != null) {
            if (saveChunks.contains(chunk)) {
                chunk.getWorld().getFormat().writeChunk(chunk);
            }
            return true;
        }
        return false;
    }

//    public void addEntity(Entity<?> entity) {
//        entities.add(entity);
//        getChunk(entity.getPos()).addEntity(entity);
//    }
//
//    public void removeEntity(Entity<?> entity) {
//        entities.remove(entity);
//        getChunk(entity.getPos()).removeEntity(entity);
//    }

    public MinecraftServer getServer() {
        return server;
    }

    public String getName() {
        return name;
    }

    public WorldFormat getFormat() {
        return format;
    }

    public TerrainGenerator getGenerator() {
        return generator;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        if (viewDistance > MAXIMUM_CHUNK_RADIUS) {
            viewDistance = MAXIMUM_CHUNK_RADIUS;
        } else if (viewDistance < MINIMUM_CHUNK_RADIUS) {
            viewDistance = MINIMUM_CHUNK_RADIUS;
        }
        this.viewDistance = viewDistance;
    }

    public Chunk getChunk(int chunkX, int chunkZ) {
        long index = Chunk.getIndex(chunkX, chunkZ);
        return loadedChunks.computeIfAbsent(index, _ -> new Chunk(new Chunk.Builder(server, this, chunkX, chunkZ)));
    }

    public Chunk getChunk(Pos pos) {
        return getChunk(pos.getChunkX(), pos.getChunkZ());
    }

    public Collection<Chunk> getLoadedChunks() {
        return loadedChunks.values();
    }

    public Material getMaterial(int x, int y, int z) {
        Chunk chunk = getChunk(x >> 4, z >> 4);
        return Material.get(chunk.getBlockId(x, y, z), chunk.getBlockMetadata(x, y, z));
    }

    @Nullable
    public Block getBlock(int x, int y, int z) {
        return getChunk(x >> 4, z >> 4).getBlock(x, y, z);
    }

    public byte getBlockId(int x, int y, int z) {
        return getChunk(x >> 4, z >> 4).getBlockId(x, y, z);
    }

    public byte getBlockMetadata(int x, int y, int z) {
        return getChunk(x >> 4, z >> 4).getBlockMetadata(x, y, z);
    }

    public byte getSkyLight(int x, int y, int z) {
        return getChunk(x >> 4, z >> 4).getSkyLight(x, y, z);
    }

    public byte getBlockLight(int x, int y, int z) {
        return getChunk(x >> 4, z >> 4).getBlockLight(x, y, z);
    }

    public void setBlock(int x, int y, int z, Material material) {
        if (!material.block) {
            return;
        }
        setBlock(x, y, z, material.blockId, (byte) material.metadata);
    }

    public final void setBlock(int x, int y, int z, byte id, byte metadata) {
        getChunk(x >> 4, z >> 4).setBlock(x, y, z, id, metadata, true);
    }

    public void scheduleBlockUpdate(int x, int y, int z) {
        scheduleBlockUpdate(x, y, z, 1);
    }

    public void scheduleBlockUpdate(int x, int y, int z, int ticksInFuture) {
        getChunk(x >> 4, z >> 4).scheduleBlockUpdate(x, y, z, ticksInFuture);
    }

    @Nullable
    public Chunk getLoadedChunk(int chunkX, int chunkZ) {
        return getLoadedChunk(Chunk.getIndex(chunkX, chunkZ));
    }

    @Nullable
    public Chunk getLoadedChunk(long chunkIndex) {
        return loadedChunks.get(chunkIndex);
    }

    @Nullable
    public TileEntity getTileEntity(int x, int y, int z) {
        Chunk chunk = getChunk(x >> 4, z >> 4);
        return chunk.getTileEntity(x, y, z);
    }

    public boolean isChunkLoaded(int x, int z) {
        return loadedChunks.containsKey(Chunk.getIndex(x, z));
    }

    // https://minecraft.fandom.com/wiki/Tick#Game_process
    @Override
    public void tick(long ticks) {
        time = worldTime + ticks;
        scheduler.tick(ticks);
        List<Chunk> chunks = new ArrayList<>(loadedChunks.values());
        List<Chunk> staleChunks = new ArrayList<>();
        for (Chunk chunk : chunks) {
            chunk.tick(ticks);
            if (chunk.isStale()) {
                staleChunks.add(chunk);
            }
        }
        staleChunks.forEach(chunk -> loadedChunks.remove(chunk.getChunkIndex()));

        inventories.forEach(inv -> inv.tick(ticks));

        super.tick(ticks);
    }

    public long getTime() {
        return time;
    }

    @Override
    public void addViewer(Player player) {
        if (isViewing(player)) {
            return;
        }

        if (player.isLoading()) {
            player.sendPacket(new LoginPacketOut(player.getEntityId(), format.getDimension()));
        } else if (player.getProtocol() >= 12) { // Beta 1.6 test build 3
            player.sendPacket(new PlayerRespawnPacketOut(format.getDimension()));
        }

        World oldWorld = player.getWorld();
        if (oldWorld != this && oldWorld != null) {
            oldWorld.removeViewer(player);
        }

        getEntitiesInChunkRange(null, player.getPos(), ENTITY_VIEW_DISTANCE_CHUNKS).forEach(entityInRange -> {
            Entity<?> entity = entityInRange.entity();
            entity.addViewer(player);
            if (entity instanceof Player) {
                // Show this player to the other player
                player.addViewer((Player) entity);
            }
        });

        viewers.add(player);

        Pos pos = player.getPos();
        int chunkX = pos.getChunkX();
        int chunkZ = pos.getChunkZ();
        int radius = Math.min(INITIAL_CHUNK_RADIUS, viewDistance);
        for (int x = chunkX - radius; x < chunkX + radius; x++) {
            for (int z = chunkZ - radius; z < chunkZ + radius; z++) {
                getChunk(x, z).addViewer(player);
            }
        }

        player.spawn();
    }

    @Override
    public void removeViewer(Player player) {
        if (!server.isRunning()) {
            return;
        }
        if (viewers.remove(player)) {
            player.removeAllViewers();
            getEntitiesInChunkRange(null, player.getPos(), ENTITY_VIEW_DISTANCE_CHUNKS).forEach(entityInRange -> entityInRange.entity().removeViewer(player));
            Collection<Chunk> chunks = new ArrayList<>(Chunk.getPlayerViewingChunks(player));
            for (Chunk chunk : chunks) {
                chunk.removeViewer(player);
            }
        }
    }

    @Override
    public List<Player> getViewers() {
        return viewers;
    }

    @Override
    public boolean isViewing(Player player) {
        return viewers.contains(player);
    }

    @Override
    public boolean hasViewers() {
        return !viewers.isEmpty();
    }

    public Pos getSpawn() {
        Pos centre = format.getSpawn();
        int centreX = (int) centre.x();
        int centreY = (int) centre.y();
        int centreZ = (int) centre.z();
        Pos spawn;
        Block block;
        List<Pos> possibleSpawns = new ArrayList<>();
        for (int x = centreX - 10; x < centreX + 10; x++) {
            for (int z = centreZ - 10; z < centreZ + 10; z++) {
                block = getBlock(x, centreY, z);
                if (block == null) {
                    continue;
                }
                if (block.id() == 0) {
                    possibleSpawns.add(new Pos(block.x() + 0.5, block.y() + 0.5, block.z() + 0.5));
                }
            }
        }
        if (possibleSpawns.isEmpty()) {
            possibleSpawns.add(centre);
        }
        spawn = possibleSpawns.get(ThreadLocalRandom.current().nextInt(possibleSpawns.size()));
        return spawn.add(0, 10, 0);
    }

    public Pos getPlayerSpawnPos(Player player) {
        return getSpawn(); // TODO
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public SortedSet<EntityInRange> getEntitiesInChunkRange(@Nullable EntityType type, Pos centre, int chunkRange) {
        SortedSet<EntityInRange> foundEntities = new TreeSet<>();
        int chunkX = centre.getChunkX();
        int chunkZ = centre.getChunkZ();
        Chunk chunk;
        List<Entity<?>> entitiesInChunk;
        for (int x = chunkX - chunkRange; x < chunkX + chunkRange; x++) {
            for (int z = chunkZ - chunkRange; z < chunkZ + chunkRange; z++) {
                chunk = getLoadedChunk(x, z);
                if (chunk == null) {
                    continue;
                }

                if (type == null) {
                    entitiesInChunk = chunk.getEntities();
                } else {
                    entitiesInChunk = chunk.getEntities(type);
                }
                for (Entity<?> entity : entitiesInChunk) {
                    foundEntities.add(new EntityInRange(entity, centre.distance(entity.getPos())));
                }
            }
        }
        return foundEntities;
    }

    public void registerInventory(Inventory inventory) {
        if (inventory instanceof PlayerInventory) {
            return;
        }
        inventories.add(inventory);
    }

    public void unregisterInventory(Inventory inventory) {
        inventories.remove(inventory);
    }

    @Override
    public void save() {
        format.save();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof World world)) {
            return false;
        }

        return world.name.equals(name);
    }

    protected void markChunkForSaving(Chunk chunk) {
        saveChunks.add(chunk);
    }

    public Set<Chunk> getAndClearChangedChunks() {
        Set<Chunk> changes = new HashSet<>(saveChunks);
        saveChunks.clear();
        return changes;
    }

    private static class TimeUpdaterTask extends ScheduledTask {
        private final World world;

        public TimeUpdaterTask(World world) {
            super(20, 20);
            this.world = world;
        }

        @Override
        public void accept(Long time) {
            TimePacketOut packet = new TimePacketOut(time);
            world.getViewers().forEach(player -> player.sendPacket(packet));
        }
    }

}
