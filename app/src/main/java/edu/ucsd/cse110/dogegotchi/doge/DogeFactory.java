package edu.ucsd.cse110.dogegotchi.doge;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.dogegotchi.R;
import edu.ucsd.cse110.dogegotchi.sprite.Coord;

public class DogeFactory {
    
    public static DogeView make(Activity activity, AccessoryView defaultAccessory) {
        // create Doge view
        Map<Doge.State, Bitmap> stateBitmaps = new HashMap<>();
        Map<Doge.State, Coord> stateCoords  = new HashMap<>();
        
        Resources resources = activity.getResources();

        // Setup views and coords per state.
        stateBitmaps.put(Doge.State.HAPPY,
                BitmapFactory.decodeResource(resources, R.drawable.happy_2x));
        stateCoords.put(Doge.State.HAPPY,
                new Coord(resources.getInteger(R.integer.happy_x),
                        resources.getInteger(R.integer.happy_y)));

        stateBitmaps.put(Doge.State.SAD,
                BitmapFactory.decodeResource(resources, R.drawable.sad_2x));
        stateCoords.put(Doge.State.SAD,
                new Coord(resources.getInteger(R.integer.sad_x),
                        resources.getInteger(R.integer.sad_y)));

        stateBitmaps.put(Doge.State.SLEEPING,
                BitmapFactory.decodeResource(resources, R.drawable.sleeping_2x));
        stateCoords.put(Doge.State.SLEEPING,
                new Coord(resources.getInteger(R.integer.sleeping_x),
                        resources.getInteger(R.integer.sleeping_y)));

        stateBitmaps.put(Doge.State.EATING,
                BitmapFactory.decodeResource(resources, R.drawable.eating_2x));
        stateCoords.put(Doge.State.EATING,
                new Coord(resources.getInteger(R.integer.eating_x),
                        resources.getInteger(R.integer.eating_y)));

        return new DogeView(activity, Doge.State.HAPPY, stateBitmaps, stateCoords, defaultAccessory);
        
    }
}
