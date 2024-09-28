package domrbeeson.gamma.task;

import java.util.function.Consumer;

public abstract class ScheduledTask implements Consumer<Long> {

    private final long delayTicks, repeatInTicks;

    private boolean cancelled = false;

    public ScheduledTask() {
        this(0);
    }

    public ScheduledTask(long delayTicks) {
        this(delayTicks, 0);
    }

    public ScheduledTask(long delayTicks, long repeatInTicks) {
        if (delayTicks < 0) {
            delayTicks = 0;
        }
        this.delayTicks = delayTicks;
        this.repeatInTicks = repeatInTicks;
    }

    public final void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public final boolean isCancelled() {
        return cancelled;
    }

    public final long getDelayTicks() {
        return delayTicks;
    }

    public final boolean isRepeating() {
        return repeatInTicks > 0;
    }

    public final long getRepeatInTicks() {
        return repeatInTicks;
    }

}
