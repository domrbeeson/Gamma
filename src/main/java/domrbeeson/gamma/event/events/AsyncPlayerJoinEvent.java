package domrbeeson.gamma.event.events;

import domrbeeson.gamma.chat.ChatColor;
import domrbeeson.gamma.chat.ChatMessage;
import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.version.MinecraftVersion;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

public class AsyncPlayerJoinEvent extends CancellableEvent implements Event.GlobalEvent {

    private final MinecraftVersion version;
    private final String username;

    private @Nullable String kickMessage = null;
    private World world;
    private @Nullable ChatMessage joinMessage;

    public AsyncPlayerJoinEvent(MinecraftVersion version, String username, World world) {
        this.version = version;
        this.username = username;
        this.world = world;
        this.joinMessage = new ChatMessage().add(ChatColor.YELLOW).add(username).add(" joined the game");
    }

    public MinecraftVersion getVersion() {
        return version;
    }

    public String getUsername() {
        return username;
    }

    public void kick(String kickMessage) {
        setCancelled(true);
        this.kickMessage = kickMessage;
    }

    public @Nullable String getKickMessage() {
        return kickMessage;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public @Nullable ChatMessage getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(@Nullable ChatMessage joinMessage) {
        this.joinMessage = joinMessage;
    }

    public void clearJoinMessage() {
        joinMessage = null;
    }

}
