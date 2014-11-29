package me.mattlogan.artiste;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Canvaser {

    Shape shape;
    Rect rect;
    Paint paint;

    public Canvaser drawShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public Canvaser inRect(Rect rect) {
        this.rect = rect;
        return this;
    }

    public Canvaser withPaint(Paint paint) {
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
