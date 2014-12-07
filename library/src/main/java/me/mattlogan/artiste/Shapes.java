package me.mattlogan.artiste;

import android.graphics.Path;
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

        @Override
        public boolean isOutlined() {
            return true;
        }
    }

    public static class Circle implements Shape {

        private Path path;

        @Override
        public void calculatePath(Rect rect, float rotationDegrees) {
            path = new Path();
            // sweep angle is mod 360, so we can't actually use 360.
            path.arcTo(new RectF(rect), 0, 359.9999f);
        }

        @Override
        public Path getPath() {
            return path;
        }
    }
}
