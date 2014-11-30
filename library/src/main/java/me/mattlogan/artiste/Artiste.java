package me.mattlogan.artiste;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Artiste {

    Shape shape;
    Paint paint;

    public Artiste drawShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public Artiste withPaint(Paint paint) {
        this.paint = paint;
        return this;
    }

    public void onCanvas(Canvas canvas) {
        if (shape == null || paint == null) {
            throw new IllegalStateException("shape, rect, and paint must be set before calling onCanvas()");
        }
        canvas.drawPath(shape.getPath(), paint);
        clearAll();
    }

    void clearAll() {
        shape = null;
        paint = null;
    }
}
