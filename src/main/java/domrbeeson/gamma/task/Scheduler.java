package domrbeeson.gamma.task;

import domrbeeson.gamma.Tickable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Scheduler implements Tickable {

    private static final Map<Long, List<ScheduledTask>> TASKS_PENDING = new ConcurrentHashMap<>();

    private long currentTime = 0;

    public <T extends ScheduledTask> void scheduleTask(T task) {
        if (task instanceof AsyncScheduledTask asyncTask) {
            // If task has no delay and isn't repeating, might as well just run it immediately
            if (asyncTask.getDelayTicks() == 0 && !asyncTask.isRepeating()) {
                CompletableFuture.runAsync(() -> task.accept(currentTime));
                return;
            }
        }
        addTask(task, task.getDelayTicks());
    }

    public void runNextTick(Consumer<Long> task) {
        runInTicks(task, 1);
    }

    public void runInTicks(Consumer<Long> task, long delayticks) {
        scheduleTask(new LazyTask(task, delayticks));
    }

    private void addTask(ScheduledTask task, long delayTicks) {
        if (task.isCancelled()) {
            return;
        }

        long runAtTime = currentTime + delayTicks;
        List<ScheduledTask> tasks = TASKS_PENDING.getOrDefault(runAtTime, new ArrayList<>());
        tasks.add(task);
        TASKS_PENDING.put(runAtTime, tasks);
    }

    @Override
    public void tick(long time) {
        currentTime = time;
        List<ScheduledTask> tasks = TASKS_PENDING.get(time);
        if (tasks == null) {
            return;
        }
        TASKS_PENDING.remove(time);

        tasks.forEach(task -> {
            if (task.isCancelled()) {
                return;
            }

            if (task instanceof AsyncScheduledTask) {
                CompletableFuture.runAsync(() -> task.accept(time));
            } else {
                task.accept(time);
            }

            if (task.isRepeating() && !task.isCancelled()) {
                addTask(task, task.getRepeatInTicks());
            }
        });
    }

    private static class LazyTask extends ScheduledTask {
        private final Consumer<Long> task;
        
        public LazyTask(Consumer<Long> task, long delayTicks) {
            super(delayTicks);
            this.task = task;
        }

        @Override
        public void accept(Long time) {
            task.accept(time);
        }
    }

}
