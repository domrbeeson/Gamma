package domrbeeson.gamma.player;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.Viewable;
import domrbeeson.gamma.chat.ChatMessage;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.entity.CollisionBox;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.metadata.LivingEntityMetadata;
import domrbeeson.gamma.event.events.PlayerQuitEvent;
import domrbeeson.gamma.inventory.CraftingInventory;
import domrbeeson.gamma.inventory.Inventory;
import domrbeeson.gamma.inventory.PlayerInventory;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.network.packet.out.*;
import domrbeeson.gamma.version.MinecraftVersion;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Player extends LivingEntity<LivingEntityMetadata> implements CommandSender, Viewable {

    public static final int SEND_CHUNKS_PER_TICK = 20;

    private final MinecraftServer server;
    private final PlayerConnection connection;
    private final MinecraftVersion version;
    private final String username;
    private final KeepAliveTask keepAliveTask;
    private final CompletableFuture<Void> loading = new CompletableFuture<>();
    private final Set<Chunk> queuedViewChunks = new HashSet<>();
    private final List<Chunk> queuedRemoveChunks = new ArrayList<>();
    private final PlayerInventory inventory;

    private @Nullable Inventory openInventory = null; // This will never be the player's inventory - that is a special case
    private Item cursorItem = Item.AIR;

    private boolean swingArmAnimation = false;
    private boolean damageAnimation = false;
    private boolean leaveBedAnimation = false;
    private boolean crouchAnimation = false;
    private boolean uncrouchAnimation = false;

    protected Player(MinecraftServer server, PlayerConnection connection, MinecraftVersion version, String username, World world, @Nullable ChatMessage joinMessage) {
        super(EntityType.PLAYER, world, Pos.ZERO, new LivingEntityMetadata(), new CollisionBox(Pos.ZERO, 0.6, 1.8), 20);
        this.server = server;
        this.connection = connection;
        this.version = version;
        this.username = username;
        inventory = new PlayerInventory(this, server.getRecipeManager());

        keepAliveTask = new KeepAliveTask(this);
        server.getScheduler().scheduleTask(keepAliveTask);

        final Pos spawnPos = world.getPlayerPos(this);
        if (world == null || spawnPos == null) {
            sendPacket(new PlayerKickPacketOut("Must specify a world and position to spawn!"));
            return;
        }
        teleport(spawnPos);
//        super.updatePos(spawnPos);

        world.addViewer(this).thenAccept(a -> {
            if (joinMessage != null) {
                sendMessage(joinMessage);
                server.broadcast(joinMessage);
            }
            loading.complete(null);
            updateSurroundingChunks(spawnPos.getChunkX(), spawnPos.getChunkZ());

            inventory.addViewer(this);
        });
    }

    public MinecraftServer getServer() {
        return server;
    }

    public PlayerConnection getConnection() {
        return connection;
    }

    public MinecraftVersion getVersion() {
        return version;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoading() {
        return !loading.isDone();
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public void sendMessage(ChatMessage message) {
        sendMessage(message.toString());
    }

    @Override
    public void sendMessage(String message) {
        if (message.length() > 100) {
            return;
        }
        sendPacket(new ChatPacketOut(message));
    }

    @Override
    public void spawn() {
        super.spawn();
        sendPacket(new SpawnPositionPacketOut(getWorld().getPlayerSpawn(this)));
        sendPacket(new TimePacketOut(getWorld().getTime()));
        // TODO send inventory
        sendPacket(new EntityVelocityPacketOut(getEntityId(), (short) 0, (short) 0, (short) 0));
        sendPacket(new PlayerPositionAndLookPacketOut(getPos(), false));
    }

    public int getProtocol() {
        return version.features.protocol();
    }

    public void teleport(Pos pos) {
        teleport(getWorld(), pos);
    }

    @Override
    public void remove() {
        loading.join();
        if (!server.getPlayerManager().remove(this)) {
            return;
        }

        World world = getWorld();
        if (world != null) {
            world.removeViewer(this);
        }

        PlayerQuitEvent event = new PlayerQuitEvent(this);
        server.call(event);

        server.getPlayerManager().remove(this);
        keepAliveTask.setCancelled(true);
        connection.getReader().close();
        connection.getWriter().close();
        try {
            connection.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String quitMessage = event.getQuitMessage();
        if (quitMessage != null) {
            server.getScheduler().runNextTick(_ -> server.broadcast(quitMessage));
        }
        System.out.println(username + " quit [players: " + server.getPlayerManager().getPlayersOnline() + "]");

        super.remove();
    }

    @Override
    public PacketOut getSpawnPacket() {
        return new PlayerSpawnPacketOut(this);
    }

    public void kick(String reason) {
        sendPacket(new PlayerKickPacketOut(reason));
        remove();
    }

    @Override
    public void tick(long ticks) {
        super.tick(ticks);

        if (isRemoved()) {
            return;
        }

        World world = getWorld();
        int sentChunks = 0;
        Chunk chunk;
        synchronized (queuedViewChunks) {
            while (queuedViewChunks.iterator().hasNext() && sentChunks < SEND_CHUNKS_PER_TICK) {
                chunk = queuedViewChunks.iterator().next();
                queuedViewChunks.remove(chunk);
                if (!world.isChunkLoaded(chunk.getChunkX(), chunk.getChunkZ())) {
                    continue;
                }
                if (chunk.isViewing(this)) {
                    continue;
                }
                chunk.addViewer(this);
                sentChunks++;
            }
        }
        sentChunks = 0;
        synchronized (queuedRemoveChunks) {
            while (queuedRemoveChunks.iterator().hasNext() && sentChunks < SEND_CHUNKS_PER_TICK) {
                chunk = queuedRemoveChunks.iterator().next();
                queuedRemoveChunks.remove(chunk);
                chunk.removeViewer(this);
                sentChunks++;
            }
        }

        inventory.tick(ticks);
        Inventory openInv = getOpenInventory();
        if (openInv instanceof CraftingInventory) {
            openInv.tick(ticks);
        }

        if (swingArmAnimation) {
            swingArmAnimation = false;

            PlayerAnimationPacketOut packet = new PlayerAnimationPacketOut(this, PlayerAnimation.SWING_ARM);
            getViewers().forEach(viewer -> viewer.sendPacket(packet));
        }
    }

    @Override
    protected void onMove(Pos oldPos, Pos newPos, boolean teleported) {
        if (teleported) {
            sendPacket(new PlayerPositionAndLookPacketOut(newPos, false));
        }
    }

    @Override
    public void onChunkChange(Chunk oldChunk, Chunk newChunk) {
        queuedViewChunks.clear();
        queuedRemoveChunks.clear();
        updateSurroundingChunks(newChunk.getChunkX(), newChunk.getChunkZ());
        List<Player> viewers = new ArrayList<>(getViewers());
        viewers.forEach(viewer -> {
            if (!isWithinChunkViewDistance(viewer.getPos())) {
                removeViewer(viewer);
                viewer.removeViewer(this);
            }
        });
        getWorld().getEntitiesInChunkRange(EntityType.PLAYER, getPos(), World.ENTITY_VIEW_DISTANCE_CHUNKS).forEach(entityInRange -> {
            Player player = (Player) entityInRange.entity();
            if (player == this) {
                return;
            }
            addViewer(player);
            player.addViewer(this);
        });
    }

    @Override
    protected void onMoveCancel() {
        sendPacket(new EntityVelocityPacketOut(getEntityId(), (short) 0, (short) 0, (short) 0));
        sendPacket(new EntityTeleportPacketOut(this, getPos()));
    }

    private void viewChunk(Chunk chunk) {
        synchronized (queuedViewChunks) {
            queuedViewChunks.add(chunk);
        }
    }

    private void hideChunk(Chunk chunk) {
        synchronized (queuedRemoveChunks) {
            queuedRemoveChunks.add(chunk);
        }
    }

    private void updateSurroundingChunks(int xCentre, int zCentre) {
        final World world = getWorld();

        int viewDistance = world.getViewDistance();

        for (int layer = 0; layer < viewDistance; layer++) {
            for (int x = xCentre - layer; x <= xCentre + layer; x++) {
                world.getChunk(x, zCentre - layer).thenAccept(this::viewChunk);
                world.getChunk(x, zCentre + layer).thenAccept(this::viewChunk);
            }
            for (int z = zCentre - layer; z <= zCentre + layer; z++) {
                world.getChunk(xCentre - layer, z).thenAccept(this::viewChunk);
                world.getChunk(xCentre + layer, z).thenAccept(this::viewChunk);
            }
        }

        int chunkX, chunkZ;
        for (Chunk chunk : Chunk.getPlayerViewingChunks(this)) { // TODO chunk can sometimes be null???????????????/
//            if (chunk == null) {
//                continue;
//            }
            chunkX = chunk.getChunkX();
            chunkZ = chunk.getChunkZ();
            if (chunkX < xCentre - viewDistance
                    || chunkX > xCentre + viewDistance
                    || chunkZ < zCentre - viewDistance
                    || chunkZ > zCentre + viewDistance
            ) {
                synchronized (queuedViewChunks) {
                    queuedViewChunks.remove(chunk); // TODO maybe synchronising here will be slow?
                }
                hideChunk(chunk); // TODO maybe just synchronise this whole block to queuedRemoveChunks
            }
        }
    }

    public void sendPacket(PacketOut packet) {
        connection.getWriter().send(getProtocol(), packet);
    }

    public void swingArm() {
        swingArmAnimation = true;
    }

    public void setCursorItem(@Nullable Item cursorItem) {
        if (cursorItem == null || (cursorItem.id() == 0 || cursorItem.amount() == 0)) {
            cursorItem = Item.AIR;
        }
        this.cursorItem = cursorItem;
        sendPacket(new WindowCursorItemPacketOut(cursorItem));
    }

    public Item getCursorItem() {
        if (cursorItem == null) {
            return Item.AIR;
        }
        return cursorItem;
    }

    public void clearCursorItem() {
        setCursorItem(null);
    }

    public @Nullable Inventory getOpenInventory() {
        return openInventory;
    }

    public void openInventory(Inventory inventory) {
        if (inventory == null) {
            return;
        }
        Inventory oldInv = openInventory;
        openInventory = inventory;
        if (oldInv != null) {
            oldInv.removeViewer(this).join();
        }
        openInventory.addViewer(this);
    }

    public void closeInventory() {
        if (openInventory != null) {
            openInventory.removeViewer(this).join();
            openInventory = null;
        }
        if (cursorItem.id() > 0) {
            // TODO drop cursor item
            sendMessage("drop cursor item here [" + cursorItem.amount() + "x " + cursorItem.id() + ":" + cursorItem.metadata() + "]");
            cursorItem = null;
        }
    }

}
