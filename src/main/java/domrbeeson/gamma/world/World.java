package domrbeeson.gamma.world;

import domrbeeson.gamma.*;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.event.EventGroup;
import domrbeeson.gamma.event.RegisteredEventListener;
import domrbeeson.gamma.event.events.PlayerMoveEvent;
import domrbeeson.gamma.event.events.WorldUnloadEvent;
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
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;

public class World extends EventGroup<Event.WorldEvent> implements Tickable, Unloadable, Viewable, Saveable {

    public static final int MAXIMUM_CHUNK_RADIUS = 15;
    public static final int INITIAL_CHUNK_RADIUS = 7;
    public static final int MINIMUM_CHUNK_RADIUS = 3;
    public static final int ENTITY_VIEW_DISTANCE_CHUNKS = 3;

    private final MinecraftServer server;
    private final Scheduler scheduler = new Scheduler();
    private final WorldManager manager;
    private final ThreadPoolExecutor threadPool;
    private final List<Player> viewers = new ArrayList<>();
    private final Map<Long, Chunk> loadedChunks = new ConcurrentHashMap<>();
    private final String name;
    private final WorldFormat format;
    private final TerrainGenerator generator;
    private final Map<Long, CompletableFuture<Chunk>> chunksCurrentlyLoading = new ConcurrentHashMap<>();
    private final long timeInFile;
    private final RegisteredEventListener<PlayerMoveEvent> playerMoveListener;
    private final CompletableFuture<Void> loadingFuture = new CompletableFuture<>();
    private final Set<Chunk> saveChunks = new HashSet<>();

    private int viewDistance;
    private CompletableFuture<Void> unloadFuture = null;
    private long time;

    protected World(MinecraftServer server, WorldManager manager, ThreadPoolExecutor threadPool, String name, WorldFormat format, TerrainGenerator generator) throws InvalidWorldFormatException {
        this.server = server;
        this.manager = manager;
        this.threadPool = threadPool;
        this.name = name;
        this.format = format;
        this.generator = generator;
        this.viewDistance = Math.max(MINIMUM_CHUNK_RADIUS, MinecraftServer.SERVER_SETTINGS.getViewDistance());

        if (!format.exists(this)) {
            format.create(this);
        }
        format.load(this);
        this.timeInFile = 0; // TODO load from world format
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

        loadingFuture.complete(null);

        System.out.println("Loaded world '" + name + "' [Format: " + format.getClass().getSimpleName() + ", Generator: " + generator.getClass().getSimpleName() + ", View distance: " + viewDistance + ", Seed: " + format.getSeed() + "]");
    }

    @Override
    public CompletableFuture<Void> unload() {
        loadingFuture.join();
        if (unloadFuture != null) {
            return unloadFuture;
        }
        WorldUnloadEvent event = new WorldUnloadEvent(this);
        server.call(event);
        List<Player> viewers = new ArrayList<>(this.viewers);
        viewers.forEach(player -> player.kick("World unloaded"));
        manager.removeWorld(this);
        playerMoveListener.stop();
        CompletableFuture<?>[] futures = new CompletableFuture[loadedChunks.size()];
        int i = 0;
        for (Chunk chunk : new HashSet<>(loadedChunks.values())) {
            futures[i] = chunk.unload();
            i++;
        }
        unloadFuture = CompletableFuture.allOf(futures);
        return unloadFuture;
    }

    protected void addChunk(Chunk chunk) {
        loadedChunks.put(chunk.getChunkIndex(), chunk);
    }

    protected CompletableFuture<Void> removeChunk(Chunk chunk) {
        if (loadedChunks.remove(chunk.getChunkIndex()) != null) {
            CompletableFuture<Void> future = new CompletableFuture<>();
            threadPool.submit(new ChunkSaver(future, chunk));
            return future;
        }
        return CompletableFuture.completedFuture(null);
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

    public CompletableFuture<Chunk> getChunk(Pos pos) {
        return getChunk((int) pos.x() >> 4, (int) pos.z() >> 4);
    }

    public Collection<Chunk> getLoadedChunks() {
        return loadedChunks.values();
    }

    @Nullable
    public Block getBlock(int x, int y, int z) {
        CompletableFuture<Chunk> future = getChunk(x >> 4, z >> 4);
        Chunk chunk = future.join();
        return chunk.getBlock(x, y, z);
    }

    public boolean setBlock(int x, int y, int z, Material material) {
        if (!material.block) {
            return false;
        }
        return setBlock(x, y, z, material.blockId, (byte) material.metadata);
    }

    public final boolean setBlock(int x, int y, int z, byte id, byte metadata) {
        getChunk(x >> 4, z >> 4).join().setBlock(x, y, z, id, metadata);
        return true;
    }

    @Nullable
    public Chunk getLoadedChunk(int chunkX, int chunkZ) {
        return loadedChunks.get(Chunk.getIndex(chunkX, chunkZ));
    }

    public CompletableFuture<Chunk> getChunk(int chunkX, int chunkZ) {
        long index = Chunk.getIndex(chunkX, chunkZ);
        Chunk chunk = loadedChunks.get(index);
        if (chunk != null) {
            return CompletableFuture.completedFuture(chunk);
        }
        CompletableFuture<Chunk> f = chunksCurrentlyLoading.get(index);
        if (f != null) {
            return f;
        }
        final CompletableFuture<Chunk> future = new CompletableFuture<>();
        chunksCurrentlyLoading.put(index, future);
        future.thenAccept(c -> {
            c.getEntities().forEach(Entity::spawn);
            chunksCurrentlyLoading.remove(index);
        });
        threadPool.submit(new ChunkLoader(future, new Chunk.Builder(server, this, chunkX, chunkZ)));
        return future;
    }

    public boolean isChunkLoaded(int x, int z) {
        return loadedChunks.containsKey(Chunk.getIndex(x, z));
    }

    // https://minecraft.fandom.com/wiki/Tick#Game_process
    @Override
    public void tick(long ticks) {
        time = timeInFile + ticks;
        scheduler.tick(ticks);
        loadedChunks.values().forEach(chunk -> chunk.tick(ticks));
        super.tick(ticks);
    }

    public long getTime() {
        return time;
    }

    @Override
    public CompletableFuture<Void> addViewer(Player player) {
        if (isViewing(player)) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.runAsync(() -> {
            if (player.isLoading()) {
                player.sendPacket(new LoginPacketOut(player.getEntityId(), format.getDimension()));
            } else if (player.getProtocol() >= 12) { // Beta 1.6 test build 3
                player.sendPacket(new PlayerRespawnPacketOut(format.getDimension()));
            }
            World oldWorld = player.getWorld();
            if (oldWorld != this && oldWorld != null) {
                oldWorld.removeViewer(player).join();
            }
            getEntitiesInChunkRange(null, player.getPos(), ENTITY_VIEW_DISTANCE_CHUNKS).forEach(entityInRange -> {
                Entity<?> entity = entityInRange.entity();
                entity.addViewer(player);
                if (entity instanceof Player) {
                    player.addViewer((Player) entity);
                }
            });
            viewers.add(player);
            sendInitialChunksToPlayer(player).join();
            player.spawn();
        });
    }

    public CompletableFuture<Void> sendInitialChunksToPlayer(Player player) {
        Pos pos = player.getPos();
        int chunkX = pos.getChunkX();
        int chunkZ = pos.getChunkZ();
        CompletableFuture<Chunk>[] futures = new CompletableFuture[INITIAL_CHUNK_RADIUS * 28];
        int i = 0;
        for (int x = chunkX - INITIAL_CHUNK_RADIUS; x < chunkX + INITIAL_CHUNK_RADIUS; x++) {
            for (int z = chunkZ - INITIAL_CHUNK_RADIUS; z < chunkZ + INITIAL_CHUNK_RADIUS; z++) {
                futures[i] = getChunk(x, z);
                futures[i].thenAccept(chunk -> {
                    chunk.addViewer(player);
                });
                i++;
            }
        }
        return CompletableFuture.allOf(futures);
    }

    @Override
    public CompletableFuture<Void> removeViewer(Player player) {
        if (viewers.remove(player)) {
            player.removeAllViewers().join();
            getEntitiesInChunkRange(null, player.getPos(), ENTITY_VIEW_DISTANCE_CHUNKS).forEach(entityInRange -> entityInRange.entity().removeViewer(player));
            Collection<Chunk> chunks = new HashSet<>(Chunk.getPlayerViewingChunks(player));
            int i = 0;
            CompletableFuture<?>[] futures = new CompletableFuture[chunks.size()];
            for (Chunk chunk : chunks) {
                futures[i] = chunk.removeViewer(player);
                i++;
            }
            return CompletableFuture.allOf(futures);
        }
        return CompletableFuture.completedFuture(null);
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

    public Pos getPlayerPos(Player player) {
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

    @Override
    public void save() {
        format.save();
    }

    protected void markChunkForSaving(Chunk chunk) {
        saveChunks.add(chunk);
    }

    public Set<Chunk> getAndClearChangedChunks() {
        Set<Chunk> changes;
        synchronized (saveChunks) {
            changes = new HashSet<>(saveChunks);
            saveChunks.clear();
        }
        return changes;
    }

    public void broadcast(String message) {
        viewers.forEach(viewer -> viewer.sendMessage(message));
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
