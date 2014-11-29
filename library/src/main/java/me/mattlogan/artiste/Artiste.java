package me.mattlogan.artiste;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Artiste {

    Shape shape;
    Rect rect;
    Paint paint;

    public Artiste drawShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public Artiste inRect(Rect rect) {
        this.rect = rect;
        return this;
    }

    public Artiste withPaint(Paint paint) {
        this.paint = paint;
        return this;
    }

    public void onCanvas(Canvas canvas) {
        if (shape == null || rect == null || paint == null) {
            throw new IllegalStateException("shape, rect, and paint must be set before calling onCanvas()");
        }
        canvas.drawPath(shape.getPathInRect(rect), paint);
        clearAll();
    }

    private void clearAll() {
        shape = null;
        rect = null;
        paint = null;
    }
}
