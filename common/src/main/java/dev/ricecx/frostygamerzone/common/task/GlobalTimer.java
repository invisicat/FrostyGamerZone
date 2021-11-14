package dev.ricecx.frostygamerzone.common.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * single threaded timer
 */
public class GlobalTimer {
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private int interval;

    private int initialDelay;

    private TimeUnit unit;

    private GlobalTask task;


    private ScheduledFuture<?> future;

    public GlobalTimer(int interval, int initialDelay, TimeUnit unit, GlobalTask task) {
        this.interval = interval;
        this.initialDelay = initialDelay;
        this.unit = unit;
        this.task = task;
    }

    public GlobalTimer start() {
        this.future = this.executor.scheduleAtFixedRate(() -> this.task.run(), this.initialDelay, this.interval, this.unit);
        return this;
    }

    public boolean isRunning() {
        return (this.future != null && !this.future.isCancelled() && !this.future.isDone());
    }

    public void terminate() {
        if (isRunning())
            this.future.cancel(true);
    }

    public long getDelay() {
        return this.future.getDelay(this.unit);
    }

    public GlobalTimer() {}
}
