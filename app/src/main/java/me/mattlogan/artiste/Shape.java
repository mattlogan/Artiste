package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.Rect;

public abstract class Shape {
    public abstract Path getPathInRect(Rect rect);
}
