package domrbeeson.gamma.world;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.Tickable;
import domrbeeson.gamma.Unloadable;
import domrbeeson.gamma.Viewable;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.BlockHandlers;
import domrbeeson.gamma.block.handler.BlockHandler;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.ItemEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.event.events.*;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.network.packet.out.BlockChangePacketOut;
import domrbeeson.gamma.network.packet.out.ChunkPacketOut;
import domrbeeson.gamma.network.packet.out.PreChunkPacketOut;
import domrbeeson.gamma.player.Player;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class Chunk implements Tickable, Unloadable, Viewable {

    private static final int RANDOM_TICK_BLOCKS_PER_SECTION = 3;
    private static final Map<Player, Set<Chunk>> PLAYERS_VIEWING_CHUNKS = new HashMap<>();

    public static final byte WIDTH = 16;
    public static final short HEIGHT = 128;
    public static final int UNLOAD_AFTER_TICKS = 200;

    private final SplittableRandom random = new SplittableRandom();
    private final MinecraftServer server;
    private final BlockHandlers blockHandlers;
    private final PreChunkPacketOut preChunkPacketLoad, preChunkPacketUnload;
    private final World world;
    private final int chunkX, chunkZ;
    private final byte[][][] blocks, metadata, blockAndSkyLight;
    private final List<Player> viewers = new ArrayList<>();
    private final long chunkIndex;
    private final List<Entity<?>> entities;
    private final Map<Short, TileEntity> tileEntities;
    private final Map<EntityType, List<Entity<?>>> entitiesByType = new HashMap<>();

    private final Map<Long, BlockChangeEvent> scheduledBlockChanges = new ConcurrentHashMap<>();
    private final Map<Long, List<Long>> scheduledBlockTicks = new HashMap<>();
    private final Map<Long, PlayerRightClickBlockEvent> scheduledBlockRightClicks = new HashMap<>();

    private boolean generated = false;
    private boolean forceLoaded = false;
    private ChunkPacketOut chunkPacket = null;
    private long timeSinceZeroPlayers;
    private @Nullable CompletableFuture<Void> unloadingFuture = null;
    private int compressionLevel = 1;

    protected Chunk(Builder builder) {
        this.server = builder.server;
        blockHandlers = server.getBlockHandlers();
        this.world = builder.world;
        this.chunkX = builder.x;
        this.chunkZ = builder.z;
        this.timeSinceZeroPlayers = world.getTime();
        this.blocks = builder.blocks;
        this.metadata = builder.metadata;;
        this.blockAndSkyLight = builder.blockAndSkyLight;
        this.entities = builder.entities;
        this.tileEntities = builder.tileEntities;

        preChunkPacketLoad = new PreChunkPacketOut(chunkX, chunkZ, true);
        preChunkPacketUnload = new PreChunkPacketOut(chunkX, chunkZ, false);
        chunkPacket = new ChunkPacketOut(this, compressionLevel);
        this.chunkIndex = getIndex(chunkX, chunkZ);
        world.addChunk(this);
    }

    public World getWorld() {
        return world;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public long getChunkIndex() {
        return chunkIndex;
    }

    private void setBlock(byte x, int y, byte z, byte id) {
        blocks[x][y][z] = id;
    }

    private void setMetadata(byte x, int y, byte z, byte metadata) {
        this.metadata[x][y][z] = metadata;
    }

    private void setLight(byte x, int y, byte z, byte blockLight, byte skyLight) {
        blockAndSkyLight[x][y][z] = (byte)(blockLight << 4 | skyLight & 15);
    }

    public byte getBlockId(int x, int y, int z) {
        return getBlockId(Block.getChunkRelativeX(x), y, Block.getChunkRelativeZ(z));
    }

    public byte getBlockId(byte x, int y, byte z) {
        return blocks[x][y][z];
    }

    public byte getBlockMetadata(byte x, int y, byte z) {
        return metadata[x][y][z];
    }

    public byte getBlockLight(byte x, int y, byte z) {
        return (byte)(blockAndSkyLight[x][y][z] >> 4);
    }

    public byte getSkyLight(byte x, int y, byte z) {
        return (byte)(blockAndSkyLight[x][y][z] & 15);
    }

    public Block getBlock(int x, int y, int z) {
        byte relativeX = Block.getChunkRelativeX(x);
        byte relativeZ = Block.getChunkRelativeZ(z);
        // TODO if Y is above build limit, return air
        return new Block(
                world,
                this,
                x,
                y,
                z,
                getBlockId(relativeX, y, relativeZ),
                getBlockMetadata(relativeX, y, relativeZ),
                getBlockLight(relativeX, y, relativeZ),
                getSkyLight(relativeX, y, relativeZ)
        );
    }

    public Block[][][] getBlocks() {
        Block[][][] blocks = new Block[WIDTH][HEIGHT][WIDTH];
        for (byte x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[x].length; y++) {
                for (byte z = 0; z < blocks[x][y].length; z++) {
                    blocks[x][y][z] = getBlock(x, y, z);
                }
            }
        }
        return blocks;
    }

    public void setBlock(int x, int y, int z, byte id) {
        setBlock(x, y, z, id, (byte) 0);
    }

    public void setBlock(int x, int y, int z, Material material) {
        if (!material.block) {
            return;
        }
        setBlock(x, y, z, material.blockId, (byte) material.metadata, true);
    }

    public void setBlock(int x, int y, int z, byte id, byte metadata) {
        setBlock(x, y, z, id, metadata, true);
    }

    private boolean areCoordsInThisChunk(int relativeX, int y, int relativeZ) {
        if (y < 0) {
            return false;
        }
        return relativeX < WIDTH
                && relativeX >= 0
                && relativeZ < WIDTH
                && relativeZ >= 0;
    }

    private void setChanged() {
        chunkPacket = null;
        world.markChunkForSaving(this);
    }

    public void setBlock(int x, int y, int z, byte id, byte metadata, boolean tick) {
        byte relativeX = Block.getChunkRelativeX(x);
        byte relativeZ = Block.getChunkRelativeZ(z);
        if (!areCoordsInThisChunk(relativeX, y, relativeZ)) {
            return;
        }
        synchronized (scheduledBlockChanges) {
            scheduledBlockChanges.put(Chunk.packChunkBlockCoords(x, y, z), new BlockChangeEvent(this, x, y, z, getBlockId(relativeX, y, relativeZ), getBlockMetadata(relativeX, y, relativeZ), id, metadata, tick));
        }
    }

    public void directlySetBlock(byte relativeX, int y, byte relativeZ, byte id, byte metadata) {
        directlySetBlock(relativeX, y, relativeZ, id, metadata, getSkyLight(relativeX, y, relativeZ), getBlockLight(relativeX, y, relativeZ));
    }

    public void directlySetBlock(byte relativeX, int y, byte relativeZ, byte id, byte metadata, byte skyLight, byte blockLight) {
        setBlock(relativeX, y, relativeZ, id);
        setMetadata(relativeX, y, relativeZ, metadata);
        setLight(relativeX, y, relativeZ, blockLight, skyLight);
        setChanged();
    }

    public void breakBlockAsPlayer(Player player, int x, int y, int z) {
        byte relativeX = Block.getChunkRelativeX(x);
        byte relativeZ = Block.getChunkRelativeZ(z);
        if (!areCoordsInThisChunk(relativeX, y, relativeZ)) {
            return;
        }
        synchronized (scheduledBlockChanges) {
            scheduledBlockChanges.put(Chunk.packChunkBlockCoords(x, y, z), new PlayerBlockBreakEvent(server, player, this, x, y, z, getBlockId(relativeX, y, relativeZ), getBlockMetadata(relativeX, y, relativeZ), true, player.getInventory().getHeldItem().id()));
        }
    }

    public void breakBlock(int x, int y, int z) {
        byte relativeX = Block.getChunkRelativeX(x);
        byte relativeZ = Block.getChunkRelativeZ(z);
        if (!areCoordsInThisChunk(relativeX, (byte) y, relativeZ)) {
            return;
        }
        synchronized (scheduledBlockChanges) {
            scheduledBlockChanges.put(Chunk.packChunkBlockCoords(relativeX, y, relativeZ), new BlockBreakEvent(server, this, x, y, z, getBlockId(relativeX, y, relativeZ), getBlockMetadata(relativeX, y, relativeZ), true));
        }
    }

    public void placeAsPlayer(Player player, int x, byte y, int z, byte id, byte metadata) {
        byte relativeX = Block.getChunkRelativeX(x);
        byte relativeZ = Block.getChunkRelativeZ(z);
        if (!areCoordsInThisChunk(relativeX, y, relativeZ)) {
            return;
        }
        synchronized (scheduledBlockChanges) {
            scheduledBlockChanges.put(packChunkBlockCoords(relativeX, y, relativeZ), new PlayerBlockPlaceEvent(player, this, x, y, z, getBlockId(relativeX, y, relativeZ), getBlockMetadata(relativeX, y, relativeZ), id, metadata, true));
        }
    }

    public void rightClickAsPlayer(Player player, int x, byte y, int z) {
        byte relativeX = Block.getChunkRelativeX(x);
        byte relativeZ = Block.getChunkRelativeZ(z);
        if (!areCoordsInThisChunk(relativeX, y, relativeZ)) {
            return;
        }
        synchronized (scheduledBlockRightClicks) {
            scheduledBlockRightClicks.put(packChunkBlockCoords(relativeX, y, relativeZ), new PlayerRightClickBlockEvent(player, x, y, z, player.getInventory().getHeldItem()));
        }
    }

    public void scheduleBlockChange(BlockChangeEvent event, long ticksInFuture) {
        long futureTick = world.getTime() + ticksInFuture;
        List<Long> scheduled = scheduledBlockTicks.getOrDefault(futureTick, new ArrayList<>());
        scheduled.add(packChunkBlockCoords(event.getX(), event.getY(), event.getZ()));
        scheduledBlockTicks.put(futureTick, scheduled);
    }

    public static long packChunkBlockCoords(int x, int y, int z) {
        return packChunkBlockCoords(Block.getChunkRelativeX(x), y, Block.getChunkRelativeZ(z));
    }

    public static long packChunkBlockCoords(byte x, int y, byte z) {
        return ((long) x << 32 | y << 8 | z);
    }

    public static byte unpackChunkBlockX(long packedX) {
        return (byte)(packedX >> 32);
    }

    public static int unpackChunkBlockY(long packedY) {
        return (int)(packedY >> 8 & 255);
    }

    public static byte unpackChunkBlockZ(long packedZ) {
        return (byte) packedZ;
    }

    public boolean isSlimeChunk() {
        return false; // TODO
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public boolean isForceLoaded() {
        return forceLoaded;
    }

    public void setForceLoaded(boolean forceLoaded) {
        this.forceLoaded = forceLoaded;
    }

    public List<Entity<?>> getEntities() {
        return entities;
    }

    public List<Entity<?>> getEntities(EntityType type) {
        return entitiesByType.getOrDefault(type, new ArrayList<>());
    }

    public boolean addEntity(Entity<?> entity) {
        if (entities.contains(entity)) {
            return false;
        }
        entities.add(entity);
        List<Entity<?>> entitiesOfType = entitiesByType.getOrDefault(entity.getType(), new ArrayList<>());
        entitiesOfType.add(entity);
        entitiesByType.put(entity.getType(), entitiesOfType);
        return true;
    }

    public boolean removeEntity(Entity<?> entity) {
        if (entities.remove(entity)) {
            List<Entity<?>> entitiesOfType = entitiesByType.get(entity.getType());
            if (entitiesOfType != null) {
                entitiesOfType.remove(entity);
                if (entitiesOfType.isEmpty()) {
                    entitiesByType.remove(entity.getType());
                }
            }
            return true;
        }
        return false;
    }

    public Collection<TileEntity> getTileEntities() {
        return tileEntities.values();
    }

    @Nullable
    public TileEntity getTileEntity(int x, int y, int z) {
        x = Block.getChunkRelativeX(x);
        z = Block.getChunkRelativeZ(z);
        if (x < 0 || y < 0 || z < 0) {
            return null;
        }
        return TileEntity.getFromPackedLocation(x, y, z, tileEntities);
    }

    public void addTileEntity(TileEntity tile) {
        tileEntities.put(tile.packLocation(), tile);
    }

    public void removeTileEntity(TileEntity tile) {
        tileEntities.remove(tile.packLocation());
    }

    @Override
    public CompletableFuture<Void> unload() {
        if (unloadingFuture != null) {
            return unloadingFuture;
        }
        if (forceLoaded && !server.isRunning()) {
            return CompletableFuture.completedFuture(null);
        }
        Entity<?> entity;
        while (!entities.isEmpty()) {
            entity = entities.removeFirst();
            entity.remove();
            entity.tick(-1); // TODO what is this for?
        }
        CompletableFuture<?>[] futures = new CompletableFuture[] {
                world.removeChunk(this),
                removeAllViewers()
        };
        unloadingFuture = CompletableFuture.allOf(futures);
        return unloadingFuture;
    }

    @Override
    public void tick(long ticks) {
        if (unloadingFuture != null) {
            return;
        }

        synchronized(scheduledBlockTicks) {
            List<Long> blocks = this.scheduledBlockTicks.get(ticks);
            if (blocks != null) {
                blocks.forEach(packed -> {
                    int x = unpackChunkBlockX(packed) + (chunkX * WIDTH);
                    int z = unpackChunkBlockZ(packed) + (chunkZ * WIDTH);
                    Block block = getBlock(x, unpackChunkBlockY(packed), z);
                    BlockTickEvent event = new BlockTickEvent(block);
                    world.call(event);
                    if (event.isCancelled()) {
                        return;
                    }
                    blockHandlers.tick(block);
                });
            }
            scheduledBlockTicks.remove(ticks);
        }

        synchronized (scheduledBlockChanges) {
            scheduledBlockChanges.values().forEach(event -> {
                world.call(event);
                if (event.isCancelled()) {
                    return;
                }

                int x = event.getX();
                int y = event.getY();
                int z = event.getZ();
                byte relativeX = Block.getChunkRelativeX(x);
                byte relativeZ = Block.getChunkRelativeZ(z);
                setBlock(relativeX, y, relativeZ, event.getNewId());
                setMetadata(relativeX, y, relativeZ, event.getNewMetadata());
                setChanged();

                BlockChangePacketOut blockChangePacket = new BlockChangePacketOut(x, y, z, getBlockId(relativeX, y, relativeZ), getBlockMetadata(relativeX, y, relativeZ));
                for (Player viewer : viewers) {
                    viewer.sendPacket(blockChangePacket);
                }

                if (event instanceof BlockBreakEvent) {
                    short toolId = 0;
                    if (event instanceof PlayerBlockBreakEvent) {
                        toolId = ((PlayerBlockBreakEvent) event).getTool();
                    }
                    List<Item> drops = blockHandlers.get(event.getCurrentId()).getDrops(server, this, x, y, z, event.getCurrentId(), event.getCurrentMetadata(), toolId);
                    BlockDropItemEvent dropItemEvent = new BlockDropItemEvent(this, x, y, z, event.getCurrentId(), event.getCurrentMetadata(), drops);
                    final Pos itemSpawnPos = new Pos(x + 0.5, y + 0.5, z + 0.5);
                    dropItemEvent.getDrops().forEach(item -> {
                        ItemEntity itemEntity = new ItemEntity(world, itemSpawnPos, item);
                        // TODO set velocity
                        EntitySpawnEvent itemSpawnEvent = new EntitySpawnEvent(itemEntity, EntitySpawnEvent.SpawnReason.BLOCK_DROP);
                        if (itemSpawnEvent.isCancelled()) {
                            return;
                        }
                        itemEntity.spawn();
                    });
                } else if (event instanceof PlayerBlockPlaceEvent) {
                    Block block = getBlock(x, y, z);
                    blockHandlers.get(block.id()).onPlace(server, block, ((PlayerBlockPlaceEvent) event).getPlayer());
                }

                BlockHandler.updateAdjacentBlocks(getBlock(x, y, z), 1);
            });
            scheduledBlockChanges.clear();
        }

        synchronized (scheduledBlockRightClicks) {
            scheduledBlockRightClicks.values().forEach(event -> {
                world.call(event);
                if (event.isCancelled()) {
                    return;
                }
                int x = event.getX();
                byte y = event.getY();
                int z = event.getZ();
                blockHandlers.get(getBlockId(Block.getChunkRelativeX(x), y, Block.getChunkRelativeZ(z))).onRightClick(server, getBlock(x, y, z), event.getPlayer());
            });
            scheduledBlockRightClicks.clear();
        }

        synchronized (entities) {
            for (int i = 0; i < entities.size(); i++) {
                if (i >= entities.size()) { // Prevents concurrent modification exception
                    break;
                }
                entities.get(i).tick(ticks);
            }
        }

        synchronized (tileEntities) {
            tileEntities.values().forEach(tile -> {
                tile.tick(ticks);
            });
        }

        byte randomX, randomZ;
        int randomY;
        byte blockId, metadata;
        for (int sectionStartY = 0; sectionStartY < HEIGHT; sectionStartY += 16) {
            for (int i = 0; i < RANDOM_TICK_BLOCKS_PER_SECTION; i++) {
                randomX = (byte) random.nextInt(WIDTH);
                randomY = random.nextInt(HEIGHT);
                randomZ = (byte) random.nextInt(WIDTH);
                blockId = getBlockId(randomX, randomY, randomZ);
                if (blockId == 0) {
                    continue;
                }
                metadata = getBlockMetadata(randomX, randomY, randomZ);
                blockHandlers.get(blockId).randomTick(server, this, WIDTH * chunkX + randomX, randomY, WIDTH * chunkZ + randomZ, blockId, metadata, ticks);
            }
        }

        if (viewers.isEmpty() && world.getTime() - timeSinceZeroPlayers >= UNLOAD_AFTER_TICKS) {
            unload();
        }
    }

    public ChunkPacketOut getChunkPacket() {
        if (chunkPacket == null) {
            chunkPacket = new ChunkPacketOut(this, compressionLevel);
        }
        return chunkPacket;
    }

    @Override
    public boolean isViewing(Player player) {
        return viewers.contains(player);
    }

    @Override
    public boolean hasViewers() {
        return !viewers.isEmpty();
    }

    @Override
    public CompletableFuture<Void> addViewer(Player player) {
        // TODO check if player is in range
//        if (player.getPos().distance(new Pos(chunkX * X_SIZE + (X_SIZE / 2d), player.getPos().y(), chunkZ * X_SIZE + (Z_SIZE / 2d))) >= world.getPlayerViewDistance()) {
//            return CompletableFuture.completedFuture(null);
//        }

        if (!isViewing(player)) {
            viewers.add(player);
            player.sendPacket(preChunkPacketLoad);
            world.getScheduler().runNextTick(ticks -> { // TODO This seems to fix most of the world holes, but need to make it more efficient
                player.sendPacket(getChunkPacket());
                entities.forEach(entity -> entity.addViewer(player));
            });

            Set<Chunk> playerChunks = PLAYERS_VIEWING_CHUNKS.getOrDefault(player, new HashSet<>());
            playerChunks.add(this);
            PLAYERS_VIEWING_CHUNKS.put(player, playerChunks);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> removeViewer(Player player) {
        if (viewers.remove(player)) {
            if (world.getServer().isRunning()) {
                player.sendPacket(preChunkPacketUnload);
            }
            if (viewers.isEmpty()) {
                timeSinceZeroPlayers = world.getTime();
            }
            Set<Chunk> playerChunks = PLAYERS_VIEWING_CHUNKS.get(player);
            if (playerChunks == null || playerChunks.isEmpty()) {
                PLAYERS_VIEWING_CHUNKS.remove(player);
            } else {
                playerChunks.remove(this);
                PLAYERS_VIEWING_CHUNKS.put(player, playerChunks);
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public List<Player> getViewers() {
        return viewers;
    }

    public void scheduleBlockTick(int x, int y, int z) {
        scheduleBlockTick(packChunkBlockCoords(x, y, z), 1);
    }

    public void scheduleBlockTick(Long packedCoords, long ticksInFuture) {
        long futureTick = world.getTime() + ticksInFuture;
        List<Long> scheduled = scheduledBlockTicks.getOrDefault(futureTick, new ArrayList<>());
        scheduled.add(packedCoords);
        scheduledBlockTicks.put(futureTick, scheduled);
    }

    public void markForSaving() {
        world.markChunkForSaving(this);
    }

    public static Collection<Chunk> getPlayerViewingChunks(Player player) {
        synchronized (PLAYERS_VIEWING_CHUNKS) {
            return PLAYERS_VIEWING_CHUNKS.getOrDefault(player, new HashSet<>());
        }
    }

    public static long getIndex(int x, int z) {
        return ByteBuffer.allocate(Long.BYTES).putInt(x).putInt(z).getLong(0);
    }

    public static class Builder {
        public final MinecraftServer server;
        public final World world;
        public final int x, z;

        private final List<Entity<?>> entities = new ArrayList<>();
        private final Map<Short, TileEntity> tileEntities = new HashMap<>();

        private byte[][][] blocks = new byte[Chunk.WIDTH][Chunk.HEIGHT][Chunk.WIDTH];
        private byte[][][] metadata = new byte[Chunk.WIDTH][Chunk.HEIGHT][Chunk.WIDTH];
        private byte[][][] blockAndSkyLight = new byte[Chunk.WIDTH][Chunk.HEIGHT][Chunk.WIDTH];

        public Builder(MinecraftServer server, World world, int x, int z) {
            this.server = server;
            this.world = world;
            this.x = x;
            this.z = z;
        }

        public Builder block(byte x, int y, byte z, byte id) {
            return block(x, y, z, id, (byte) 0, (byte) 15, (byte) 15);
        }

        public Builder block(byte x, int y, byte z, byte id, byte metadata) {
            return block(x, y, z, id, metadata, (byte) 15, (byte) 15);
        }

        public Builder block(byte x, int y, byte z, byte id, byte metadata, byte blockLight, byte skyLight) {
            blocks[x][y][z] = id;
            this.metadata[x][y][z] = metadata;
            blockAndSkyLight[x][y][z] = (byte)(blockLight & 15 << 4 | skyLight & 15);
            return this;
        }

        public Builder entity(Entity<?> entity) {
            entities.add(entity);
            return this;
        }

        public Builder tileEntity(TileEntity tileEntity) {
            tileEntities.put(tileEntity.packLocation(), tileEntity);
            return this;
        }
    }

}