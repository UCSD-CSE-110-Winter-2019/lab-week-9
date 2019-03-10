package edu.ucsd.cse110.dogegotchi.doge;

import android.graphics.Canvas;

import edu.ucsd.cse110.dogegotchi.accessories.IDrawable;
import edu.ucsd.cse110.dogegotchi.sprite.BitmapSprite;

public class AccessoryView extends BitmapSprite {

    private IDrawable drawable;

    public AccessoryView(IDrawable drawable) {
        this.drawable = drawable;
    }

    public void drawView(Canvas canvas) {
        drawable.draw(canvas);
    }

}
