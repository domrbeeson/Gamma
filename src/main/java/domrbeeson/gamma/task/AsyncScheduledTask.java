package domrbeeson.gamma.task;

public abstract class AsyncScheduledTask extends ScheduledTask {

    public AsyncScheduledTask() {
        super();
    }

    public AsyncScheduledTask(long delayTicks) {
        super(delayTicks);
    }

    public AsyncScheduledTask(long delayTicks, long repeatInTicks) {
        super(delayTicks, repeatInTicks);
    }

}
