package domrbeeson.gamma.event.events;

import domrbeeson.gamma.chat.ChatMessage;
import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.player.Player;

public class AsyncPlayerChatEvent extends CancellableEvent implements Event.GlobalEvent {

    private final Player player;

    private ChatMessage message;

    public AsyncPlayerChatEvent(Player player, String message) {
        this.player = player;
        this.message = new ChatMessage(message);
    }

    public Player getPlayer() {
        return player;
    }

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

}
