package domrbeeson.gamma.event.events;

import domrbeeson.gamma.event.Cancellable;

public class CancellableEvent implements Cancellable {

    private boolean cancelled = false;

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

}
