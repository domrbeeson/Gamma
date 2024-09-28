package domrbeeson.gamma.event.events;

import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.player.Player;

public abstract class PlayerWorldEvent extends CancellableEvent implements Event.WorldEvent {
    
    private final Player player;

    private boolean cancelled;

    public PlayerWorldEvent(Player player) {
        this.player = player;
    }

    public final Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
