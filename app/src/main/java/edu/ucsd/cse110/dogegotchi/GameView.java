package edu.ucsd.cse110.dogegotchi;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

import edu.ucsd.cse110.dogegotchi.daynightcycle.IDayNightCycleObserver;
import edu.ucsd.cse110.dogegotchi.sprite.ISprite;
import edu.ucsd.cse110.dogegotchi.ticker.ITickerObserver;

/**
 * Controller of the canvas on which all sprites are drawn.
 */
public class GameView
        extends SurfaceView
        implements SurfaceHolder.Callback, ITickerObserver, IDayNightCycleObserver {
    // background images
    Bitmap bg_day   = BitmapFactory.decodeResource(getResources(), R.drawable.doge_house_day_2x),
           bg_night = BitmapFactory.decodeResource(getResources(), R.drawable.doge_house_night_2x_test),
           bg = bg_day;

    boolean canvasCreated = false;

    private Collection<ISprite> sprites;

    /**
     * @see SurfaceView#SurfaceView(Context, AttributeSet)
     */
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);

        setFocusable(true);
    }

    public GameView(final Context context) {
        this(context, null);
    }

    public void setSprites(final Collection<ISprite> sprites) {
        this.sprites = ImmutableList.copyOf(sprites);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) { canvasCreated = true; onTick(); }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { canvasCreated = false; }

    /**
     * Re-draws the game view with up-to-date states of different game entities.
     */
    public void onTick() {
        if (canvasCreated) {
            Canvas canvas = null;

            try {
                Log.i(this.getClass().getSimpleName(), ">\tAttempting to draw on canvas...");

                // 1. Acquire lock on the canvas (as multiple views will be writing to it)
                canvas = this.getHolder().lockCanvas();

                // 2. Update the canvas
                synchronized (this.getHolder()) {
                    Log.i(this.getClass().getSimpleName(), ">\tDrawing on canvas...");
                    this.draw(canvas);
                }
            } catch (Exception exception) {
                Log.e(this.getClass().getSimpleName(),
                        "🛑 Failed to draw on canvas: " + exception.getMessage(), exception);
            } finally {
                if (canvas != null) {
                    try {
                        // 3. Release the lock and update the canvas
                        this.getHolder().unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.i(this.getClass().getSimpleName(), "[DONE] Drew on canvas.");
        }
    }

    /**
     * Draws the background and all sprites in {@link #sprites} on the {@code canvas}.
     *
     * @param canvas Canvas on which to draw.
     */
    @Override
    public void draw(final Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {
            canvas.drawColor(Color.parseColor("#107594"));
            canvas.drawBitmap(bg, 0, 0, null);

            sprites.forEach(sprite -> sprite.draw(canvas));
        }
    }

    /**
     * Updates the background to that of the current period, and
     * plays the corresponding background music. Music is set to
     * loop so that if it's shorter than the period it keeps playing.
     *
     * @param newPeriod Indicates whether day or night just started.
     */
    @Override
    public void onPeriodChange(Period newPeriod) {
        Log.i(this.getClass().getSimpleName(), "It is now: " + newPeriod);

        if (newPeriod == Period.DAY) {
            bg = bg_day;
        } else if (newPeriod == Period.NIGHT) {
            bg = bg_night;
        }

        onTick(); // to force a draw
    }
}
