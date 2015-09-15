package me.mattlogan.artiste;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

final class MathUtils {

    private MathUtils() {
        throw new AssertionError("No instances allowed");
    }

    static float slope(float[] point1, float[] point2) {
        return (point2[1] - point1[1]) / (point2[0] - point1[0]);
    }

    static float distance(float x1, float y1, float x2, float y2) {
        return (float) sqrt(pow(y2 - y1, 2) + pow(x2 - x1, 2));
    }

    static float yIntercept(float[] point, float slope) {
        return point[1] - slope * point[0];
    }
}
