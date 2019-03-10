package edu.ucsd.cse110.dogegotchi.ticker;

import android.os.AsyncTask;
import android.util.Log;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Uses an {@link AsyncTask} to implement a {@link ITicker}.
 *
 * @see ITicker
 */
public class AsyncTaskTicker
        extends AsyncTask<Void, Void, Void>
        implements ITicker
{
    int counter = 0;
    /**
     * Observers of this ticker.
     */
    private final Collection<ITickerObserver> observers;

    /**
     * Duration, in seconds, of the wait between ticks.
     */
    private final Duration tickInterval;

    /**
     * Constructor.
     *
     * @param observers List of observers.
     * @param tickInterval Duration in seconds of wait between ticks.
     */
    public AsyncTaskTicker(final Collection<ITickerObserver> observers,
                           final int tickInterval)
    {
        this.observers = observers;
        this.tickInterval = Duration.ofSeconds(tickInterval);
    }

    /**
     * Constructor.
     *
     * @param tickInterval Duration in seconds of wait between ticks.
     */
    public AsyncTaskTicker(final int tickInterval) {
        this(new ArrayList<>(), tickInterval);
    }

    /**
     * Switch for turning flag on/off.
     */
    private volatile boolean running = false;

    public void register(final ITickerObserver observer) {
        observers.add(observer);
    }

    public void unregister(final ITickerObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void start() {
        this.running = true;
        super.execute();
    }

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    protected Void doInBackground(Void... none) {
        while (running) {
            try {
                // do tick
                publishProgress();

                /**
                 * Refresh canvas every {@link tickInterval} seconds.
                 */
                Log.i(this.getClass().getSimpleName(), "Sleeping for " + tickInterval.getSeconds() + " seconds.");
                Thread.sleep(tickInterval.toMillis());
                Log.i(this.getClass().getSimpleName(), "Slept for " + tickInterval.getSeconds() + " seconds.");
            } catch (InterruptedException e) {
                Log.e(this.getClass().getSimpleName(), e.getMessage(), e);
            }
        }

        Log.i(this.getClass().getSimpleName(), "[DONE] Exiting game loop.");

        // quirk to get around Void return type
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... none) {
        doTick();
    }

    private void doTick() {
        if (counter++ % 2 == 0) {
            Log.i(this.getClass().getSimpleName(), "🕰 Tick...");
            observers.forEach(ITickerObserver::onTick);
        }
    }
}
