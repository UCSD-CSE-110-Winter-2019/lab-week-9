package edu.ucsd.cse110.dogegotchi.accessories;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import edu.ucsd.cse110.dogegotchi.R;
import edu.ucsd.cse110.dogegotchi.doge.AccessoryView;
import edu.ucsd.cse110.dogegotchi.doge.Doge;

public class AccessoryFactory {

    public Bitmap bone, ham, steak, turkey;

    public enum Accessory {
        BONE, HAM, STEAK, TURKEY
    }

    public AccessoryFactory(Activity activity) {
        bone = BitmapFactory.decodeResource(activity.getResources(), R.drawable.dogbone_2x);
        ham = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ham_2x);
        steak = BitmapFactory.decodeResource(activity.getResources(), R.drawable.steak_2x);
        turkey = BitmapFactory.decodeResource(activity.getResources(), R.drawable.turkey_leg_2x);
    }

    public AccessoryView make(Doge.State state) {
        switch(state) {
            case SAD: return new AccessoryView(new DrawableText("im sad gibe me food plz"));
            case SLEEPING: return new AccessoryView(new DrawableText("Zzzzzzzz....."));
            case HAPPY: return new AccessoryView(new DrawableSprite(bone));
        }

        return null;
    }

    public AccessoryView make(Accessory accessory) {
        switch(accessory) {
            case BONE: return new AccessoryView(new DrawableSprite(bone));
            case HAM: return new AccessoryView(new DrawableSprite(ham));
            case STEAK: return new AccessoryView(new DrawableSprite(steak));
            case TURKEY: return new AccessoryView(new DrawableSprite(turkey));
        }

        return null;
    }
}
