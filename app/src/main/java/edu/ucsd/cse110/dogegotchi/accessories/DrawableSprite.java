package edu.ucsd.cse110.dogegotchi.accessories;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import edu.ucsd.cse110.dogegotchi.sprite.Coord;

public class DrawableSprite implements IDrawable {

    private Bitmap sprite;
    private Coord coord;

    public DrawableSprite(Bitmap bitmap) {
        sprite = bitmap;
        coord = new Coord(600, 500);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(sprite, coord.getX(), coord.getY(), null);
    }
}
