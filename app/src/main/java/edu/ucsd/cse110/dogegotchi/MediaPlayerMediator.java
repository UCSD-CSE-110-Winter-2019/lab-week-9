package edu.ucsd.cse110.dogegotchi;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

import edu.ucsd.cse110.dogegotchi.daynightcycle.IDayNightCycleObserver;

public class MediaPlayerMediator implements IDayNightCycleObserver {

    private MediaPlayer dayPlayer, nightPlayer;

    public MediaPlayerMediator(Activity activity) {
        this.dayPlayer = MediaPlayer.create(activity, R.raw.daytime_short);
        this.nightPlayer = MediaPlayer.create(activity, R.raw.night_time);

        try {
            dayPlayer.prepare();
            nightPlayer.prepare();
        }
        catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Failed to prepare media player.");
        }
    }

    public void destroy() {
        if (this.dayPlayer.isPlaying()) {
            this.dayPlayer.stop();
        }
        if (this.nightPlayer.isPlaying()) {
            this.nightPlayer.stop();
        }
    }

    @Override
    public void onPeriodChange(Period newPeriod) {
        if (newPeriod == Period.DAY) {
            if (nightPlayer.isPlaying()) {
                nightPlayer.pause();
                nightPlayer.seekTo(0); // rewind
            }
            dayPlayer.start();
            dayPlayer.setLooping(true);
        } else if (newPeriod == Period.NIGHT) {
            if (dayPlayer.isPlaying()) {
                dayPlayer.pause();
                dayPlayer.seekTo(0); // rewind
            }
            nightPlayer.start();
            nightPlayer.setLooping(true);
        }
    }
}
