package edu.ucsd.cse110.dogegotchi.doge;

import android.util.Log;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;

import edu.ucsd.cse110.dogegotchi.daynightcycle.IDayNightCycleObserver;
import edu.ucsd.cse110.dogegotchi.observer.ISubject;
import edu.ucsd.cse110.dogegotchi.ticker.ITickerObserver;

/**
 * Logic for our friendly, sophisticated doge.
 */
public class Doge implements ISubject<IDogeObserver>, ITickerObserver, IDayNightCycleObserver {
    /**
     * Current number of ticks. Reset after every potential mood swing.
     */
    int numTicks;

    /**
     * How many ticks before we toss a multi-sided die to check mood swing.
     */
    final int numTicksBeforeMoodSwing;

    /**
     * Probability of a mood swing every {@link #numTicksBeforeMoodSwing}.
     */
    final double moodSwingProbability;

    /**
     * State of doge.
     */
    State state;

    private Collection<IDogeObserver> observers;

    /**
     * Constructor.
     *
     * @param numTicksBeforeMoodSwing Number of ticks before checking for mood swing.
     * @param moodSwingProbability Probability of a mood swing every {@link #numTicksBeforeMoodSwing}.
     */
    public Doge(final int numTicksBeforeMoodSwing, final double moodSwingProbability) {
        Preconditions.checkArgument(
                0.0 <= moodSwingProbability && moodSwingProbability < 1.0f,
                "Mood swing probability must be in range [0,1).");

        this.numTicks = 0;
        this.numTicksBeforeMoodSwing = numTicksBeforeMoodSwing;
        this.moodSwingProbability = moodSwingProbability;
        this.state = State.HAPPY;
        this.observers = new ArrayList<>();
        Log.i(this.getClass().getSimpleName(), String.format(
                "Creating Doge with initial state %s, with mood swing prob %.2f"
                + "and num ticks before each swing attempt %d",
                this.state, this.moodSwingProbability, this.numTicksBeforeMoodSwing));
    }

    @Override
    public void onTick() {
        this.numTicks++;

        if (this.numTicks > 0
            && (this.numTicks % this.numTicksBeforeMoodSwing) == 0) {
            tryRandomMoodSwing();
            this.numTicks = 0;
        }
    }

    /**
     * **Strictly follow** the Finite State Machine in the write-up.
     */
    private void tryRandomMoodSwing() {
        if (state == State.EATING) {
            setState(State.HAPPY);
        }
        else if(state == State.HAPPY && Math.random() < moodSwingProbability) {
            setState(State.SAD);
        }
    }

    @Override
    public void register(IDogeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(IDogeObserver observer) {
        observers.remove(observer);
    }

    /**
     * Updates the state of our friendly doge and updates all observers.
     *
     * Note: observe how by using a setter we guarantee that side effects of
     *       an update occur, namely notifying the observers. And it's unused
     *       right now, hm...
     */
    private void setState(final Doge.State newState) {
        this.state = newState;
        Log.i(this.getClass().getSimpleName(), "Doge state changed to: " + newState);
        this.observers.forEach(observer -> observer.onStateChange(newState));
    }

    @Override
    public void onPeriodChange(Period newPeriod) {
        if (newPeriod == Period.DAY) {
            setState(State.HAPPY);
        } else {
            setState(State.SLEEPING);
        }
    }

    public void onFoodPresented() {
        setState(State.EATING);
    }

    /**
     * Moods and actions for our doge.
     */
    public enum State {
        HAPPY,
        SAD,
        SLEEPING,
        EATING;
    }
}
