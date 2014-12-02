package me.mattlogan.artiste;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class Shape {
    public abstract void setRotation(int rotationDegrees);
    public abstract void setBounds(Rect rect);  // calculation heavy, should be called outside of onDraw()
    public abstract void draw(Canvas canvas, Paint paint);
}
