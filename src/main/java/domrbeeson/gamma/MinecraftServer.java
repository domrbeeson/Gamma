package domrbeeson.gamma;

import domrbeeson.gamma.chat.ChatMessage;
import domrbeeson.gamma.command.CommandManager;
import domrbeeson.gamma.command.ConsoleCommandReader;
import domrbeeson.gamma.crafting.RecipeManager;
import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.event.EventGroup;
import domrbeeson.gamma.event.events.server.ServerStopEvent;
import domrbeeson.gamma.player.PlayerManager;
import domrbeeson.gamma.settings.ServerSettings;
import domrbeeson.gamma.settings.SettingsFile;
import domrbeeson.gamma.task.Scheduler;
import domrbeeson.gamma.task.tasks.TpsTask;
import domrbeeson.gamma.world.World;
import domrbeeson.gamma.world.WorldManager;
import domrbeeson.gamma.world.format.AlphaWorldFormat;
import domrbeeson.gamma.world.terrain.AlphaGenerator;

public final class MinecraftServer extends EventGroup<Event.GlobalEvent> implements Stoppable {

    public static final long SERVER_START_MILLIS = System.currentTimeMillis();
    public static final long TICK_EVERY_MILLIS = 50;
    public static final ServerSettings SERVER_SETTINGS = new SettingsFile().getServerSettings();

    private final WorldManager worldManager = new WorldManager(this);
    private final PlayerManager playerManager = new PlayerManager(this);
    private final Scheduler scheduler = new Scheduler();
    private final RecipeManager recipeManager = new RecipeManager();
    private final CommandManager commandManager = new CommandManager(this);
    private final ConsoleCommandReader consoleReader = new ConsoleCommandReader(this, commandManager);
    private final TpsTask tpsTask = new TpsTask();

    private boolean running = true;
    private long tick = 0;

    public MinecraftServer() throws InterruptedException {
        World defaultWorld = worldManager.loadOrCreateWorld(SERVER_SETTINGS.getDefaultWorldName(), new AlphaWorldFormat(), new AlphaGenerator());
        if (defaultWorld != null) {
            defaultWorld.setViewDistance(SERVER_SETTINGS.getViewDistance());
            worldManager.setDefaultWorld(defaultWorld);
        }

        System.out.println("Server started with minimum protocol " + SERVER_SETTINGS.getMinecraftVersion().features.protocol() + " [" + SERVER_SETTINGS.getMinecraftVersion().name + "] on port " + SERVER_SETTINGS.getPort() + " (" + (System.currentTimeMillis() - SERVER_START_MILLIS) + "ms)");

        scheduler.scheduleTask(tpsTask);

        long start, timeout;
        while (running) {
            start = System.currentTimeMillis();

            super.tick(tick);

            scheduler.tick(tick);
            worldManager.tick(tick);

            if (tick % 20 == 0) {
                System.out.println("Memory [" + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000) + "MB/" + (Runtime.getRuntime().totalMemory() / 1000000) + "MB] [" + (Runtime.getRuntime().maxMemory() / 1000000) + "MB max]");
            }

            timeout = TICK_EVERY_MILLIS - (System.currentTimeMillis() - start);
            if (timeout > 0) {
                Thread.sleep(timeout);
            }

            tick++;
        }
    }

    public long getTick() {
        return tick;
    }

    public void stop() {
        System.out.println("Stopping server");
        running = false;
        consoleReader.stop();
        tpsTask.stop();

        ServerStopEvent event = new ServerStopEvent(this);
        call(event);

        System.out.println("Unloading " + worldManager.getWorlds().size() + " worlds");
        worldManager.unload();
        System.out.println("Unloaded worlds");
    }

    public boolean isRunning() {
        return running;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public void broadcast(ChatMessage message) {
        broadcast(message.toString());
    }

    public void broadcast(String message) {
        if (running) {
            playerManager.getPlayers().forEach(player -> {
                player.sendMessage(message);
            });
        }
    }

    public static void main(String... args) throws InterruptedException {
        new MinecraftServer();
    }
}
