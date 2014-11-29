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
}
