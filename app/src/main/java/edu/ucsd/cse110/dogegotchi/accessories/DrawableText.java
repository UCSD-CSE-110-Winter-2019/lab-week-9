package edu.ucsd.cse110.dogegotchi.accessories;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import edu.ucsd.cse110.dogegotchi.sprite.Coord;

public class DrawableText implements IDrawable {

    private String text;
    private Coord coord;

    public DrawableText(String message) {
        text = message;
        coord = new Coord(600, 300);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);

        canvas.drawText(text, coord.getX(), coord.getY(), paint);
    }
}
