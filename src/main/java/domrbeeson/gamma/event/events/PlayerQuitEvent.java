package domrbeeson.gamma.event.events;

import domrbeeson.gamma.chat.ChatColor;
import domrbeeson.gamma.chat.ChatMessage;
import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.player.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerQuitEvent implements Event.GlobalEvent {

    private final Player player;

    private @Nullable String quitMessage;

    public PlayerQuitEvent(Player player) {
        this.player = player;

        quitMessage = new ChatMessage().add(ChatColor.YELLOW).add(player.getUsername() + " left the game").toString();
    }

    public Player getPlayer() {
        return player;
    }

    public void setQuitMessage(ChatMessage message) {
        quitMessage = message.toString();
    }

    public void clearQuitMessage() {
        quitMessage = null;
    }

    public @Nullable String getQuitMessage() {
        return quitMessage;
    }

}
