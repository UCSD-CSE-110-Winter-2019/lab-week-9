package edu.ucsd.cse110.dogegotchi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Collections;

import edu.ucsd.cse110.dogegotchi.accessories.AccessoryFactory;
import edu.ucsd.cse110.dogegotchi.daynightcycle.DayNightCycleMediator;
import edu.ucsd.cse110.dogegotchi.doge.Doge;
import edu.ucsd.cse110.dogegotchi.doge.DogeFactory;
import edu.ucsd.cse110.dogegotchi.doge.DogeView;
import edu.ucsd.cse110.dogegotchi.food.FoodMediator;
import edu.ucsd.cse110.dogegotchi.food.FoodView;
import edu.ucsd.cse110.dogegotchi.ticker.AsyncTaskTicker;
import edu.ucsd.cse110.dogegotchi.ticker.ITicker;

/**
 * In reading this class observe how we use the xml resource files for
 * constants, instead of making them static pieces of code.
 */
public class MainActivity extends Activity {

    private ITicker ticker;

    private DayNightCycleMediator dayNightCycleMediator;

    private FoodMediator foodMediator;

    private MediaPlayerMediator mediaPlayerMediator;

    private Doge doge;

    private DogeView dogeView;

    private AccessoryFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make game fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // start the ticker
        this.ticker = new AsyncTaskTicker(getResources().getInteger(R.integer.tick_interval));

        /**
         * create day night cycle tracker
         * Note: we implemented this for you, but do read the code to understand it.
         */
        int ticksPerPeriod = getResources().getInteger(R.integer.ticks_per_period);
        this.dayNightCycleMediator = new DayNightCycleMediator(ticksPerPeriod);
        ticker.register(this.dayNightCycleMediator);

        mediaPlayerMediator = new MediaPlayerMediator(this);
        dayNightCycleMediator.register(mediaPlayerMediator);

        factory = new AccessoryFactory(this);

        /**
         * - You'll have to make doge's state change from Happy/Sad at night to Sleeping.
         * - In the morning, doge should go to happy state. See write-up.
         */
        // create the almighty doge
        createDoge(ticksPerPeriod);
        this.dayNightCycleMediator.register(this.doge);
        ticker.register(this.doge);

        final GameView gameView = this.findViewById(R.id.GameCanvasView);
        gameView.setSprites(Collections.singletonList(this.dogeView));

        ticker.register(gameView);
        this.dayNightCycleMediator.register(gameView);

        foodMediator = new FoodMediator(factory, new FoodView(this), this.doge, this.dogeView);

        // Action! 🎬
        ticker.start();
        Log.i("main", "Here we go...");
    }

    /**
     * Creational logic for Doge and DogeView.
     *
     * Refactor {@link Doge} and/or {@link DogeView} accordingly using the Observer pattern
     * so that our doge goes to sleep at night. When waking up in the morning, the Doge should
     * be {@link edu.ucsd.cse110.dogegotchi.doge.Doge.State#HAPPY}, regardless of previous state.
     *
     * @param ticksPerPeriod Number of ticks per {@link edu.ucsd.cse110.dogegotchi.daynightcycle.IDayNightCycleObserver.Period} period.
     */
    private void createDoge(final int ticksPerPeriod) {
        // create Doge model
        int ticksPerMoodSwing = ticksPerPeriod/getResources().getInteger(R.integer.mood_swings_per_period);
        double moodSwingProbability = getResources().getInteger(R.integer.mood_swing_probability)/100.0;
        this.doge = new Doge(ticksPerMoodSwing, moodSwingProbability);
        this.dogeView = DogeFactory.make(this, factory.make(Doge.State.HAPPY));

        // make the doge view observe doge's mood swings
        this.doge.register(this.dogeView);
    }

    @Override
    public void onDestroy() {
        mediaPlayerMediator.destroy();
        this.ticker.stop();
        super.onDestroy();
    }
}
