package me.mattlogan.artiste;

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
}
