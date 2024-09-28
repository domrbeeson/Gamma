package domrbeeson.gamma.task.tasks;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.task.ScheduledTask;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TpsTask extends ScheduledTask implements Runnable {

    private static final long CHECK_INTERVAL_MILLIS = 5_000;

    private final Thread thread;

    private long lastCalculatedMillis = System.currentTimeMillis();
    private double tps = (int) (1_000 / MinecraftServer.TICK_EVERY_MILLIS);
    private int ticksSinceLastCalculated = 0;

    public TpsTask() {
        super(0, 1);
        thread = new Thread(this, "TPS Calculator");
        thread.start();
    }

    @Override
    public void accept(Long aLong) {
        synchronized (thread) {
            ticksSinceLastCalculated++;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(CHECK_INTERVAL_MILLIS);
            } catch (InterruptedException e) {
                return;
            }
            long now = System.currentTimeMillis();
            synchronized (thread) {
                tps = new BigDecimal(ticksSinceLastCalculated / ((now - lastCalculatedMillis) / 1000d)).setScale(1, RoundingMode.HALF_UP).doubleValue();
//                System.out.println("TPS: " + tps + ", ticksSinceLastCalculated: " + ticksSinceLastCalculated);
                lastCalculatedMillis = now;
                ticksSinceLastCalculated = 0;
            }
        }
    }

    public void stop() {
        thread.interrupt();
    }

    public double getTps() {
        return tps;
    }

}
