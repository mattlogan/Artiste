package me.mattlogan.artiste;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Shapes {

    public static class Triangle extends RegularConvexPolygon {
        @Override
        public int getNumberOfSides() {
            return 3;
        }
    }

    public static class Square extends RegularConvexPolygon {
        @Override
        public int getNumberOfSides() {
            return 4;
        }
    }

    public static class Pentagon extends RegularConvexPolygon {
        @Override
        public int getNumberOfSides() {
            return 5;
        }
    }

    public static class Hexagon extends RegularConvexPolygon {
        @Override
        public int getNumberOfSides() {
            return 6;
        }
    }

    public static class FivePointedStar extends RegularStarPolygon {
        @Override
        public int getNumberOfPoints() {
            return 5;
        }

        @Override
        public int getDensity() {
            return 2;
        }
    }

    public static class Circle extends Shape {

        RectF rectF;

        @Override
        public void setRotation(float rotationDegrees) {
            // ignore this
        }

        @Override
        public void setBounds(Rect rect) {
            rectF = new RectF(rect);
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            canvas.drawArc(rectF, 0, 360, false, paint);
        }
    }
}
