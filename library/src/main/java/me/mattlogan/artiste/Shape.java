package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.Rect;

public abstract class Shape {
    public abstract void setBounds(Rect rect);  // calculation heavy, should be called outside of onDraw()
    public abstract Path getPath();
}
