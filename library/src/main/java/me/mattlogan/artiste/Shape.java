package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.Rect;

public interface Shape {
    public abstract void calculatePath(Rect rect, float rotationDegrees);  // calculation heavy, should be called outside of onDraw()
    public abstract Path getPath();
}
