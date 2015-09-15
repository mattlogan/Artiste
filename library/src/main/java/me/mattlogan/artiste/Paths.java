package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.RectF;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static me.mattlogan.artiste.MathUtils.*;

public final class Paths {

    private Paths() {
        throw new AssertionError("No instances allowed");
    }

    /**
     * Creates a regular convex polygon {@link android.graphics.Path}.
     *
     * @param left            Left bound
     * @param top             Top bound
     * @param right           Right bound
     * @param bottom          Bottom bound
     * @param numSides        Number of sides
     * @param rotationDegrees Degrees to rotate polygon
     * @return A {@link android.graphics.Path} corresponding to a regular convex polygon.
     */
    public static Path regularConvexPolygon(int left, int top, int right, int bottom,
                                            int numSides, float rotationDegrees) {

        if (right - left != bottom - top) {
            throw new IllegalArgumentException("Provided bounds (" + left + ", " + top + ", " +
                    right + ", " + bottom + ") must be square.");
        }

        if (numSides < 3) {
            throw new IllegalArgumentException("Number of sides must be at least 3.");
        }

        float radius = (right - left) / 2f;

        float degreesBetweenPoints = 360f / numSides;

        // Add 90 so first point is top
        float baseRotation = 90 + rotationDegrees;

        // Assume we want a point of the polygon at the top unless otherwise set
        float startDegrees = numSides % 2 != 0 ?
                baseRotation : baseRotation + degreesBetweenPoints / 2f;

        Path path = new Path();

        for (int i = 0; i <= numSides; i++) {
            double theta = toRadians(startDegrees + i * degreesBetweenPoints);

            float x = (float) (left + radius + (radius * cos(theta)));
            float y = (float) (top + radius - (radius * sin(theta)));

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        return path;
    }

    /**
     * Creates a regular star polygon {@link android.graphics.Path}.
     *
     * @param left            Left bound
     * @param top             Top bound
     * @param right           Right bound
     * @param bottom          Bottom bound
     * @param numPoints       Number of points on star
     * @param density         Density of the star polygon (the number of vertices, or points, to
     *                        skip when drawing a line connecting two vertices.)
     * @param rotationDegrees Number of degrees to rotate star polygon
     * @param outline         True if only the star's outline should be drawn. If false, complete lines
     *                        will be drawn connecting the star's vertices.
     * @return A {@link android.graphics.Path} corresponding to a regular star polygon.
     */
    public static Path regularStarPolygon(int left, int top, int right, int bottom,
                                          int numPoints, int density, float rotationDegrees,
                                          boolean outline) {

        if (right - left != bottom - top) {
            throw new IllegalArgumentException("Provided bounds (" + left + ", " + top + ", " +
                    right + ", " + bottom + ") must be square.");
        }
        if (numPoints < 5) {
            throw new IllegalArgumentException("Number of points must be at least 5");
        }
        if (density < 2) {
            throw new IllegalArgumentException("Density must be at least 2");
        }

        float radius = (right - left) / 2f;

        // Add 90 so first point is top
        float startDegrees = 90 + rotationDegrees;

        float[][] outerPointsArray = makeOuterPointsArray(numPoints, density, startDegrees, radius);

        if (outline) {
            // Find the first intersection point created by drawing each line in the star
            float[] firstIntersection = findFirstIntersectionPoint(outerPointsArray);

            // Use the first intersection point to find the radius of the inner circle of the star
            float innerRadius = distance(radius, radius, firstIntersection[0],
                    firstIntersection[1]);

            // Make the array of each point in the star outline
            float[][] outlinePointsArray = makeOutlinePointsArray(numPoints * 2, startDegrees,
                    radius, innerRadius);

            return createStarPathFromPointsArray(left, top, outlinePointsArray);
        } else {
            return createStarPathFromPointsArray(left, top, outerPointsArray);
        }
    }

    /**
     * Creates a circle {@link android.graphics.Path}.
     *
     * @param left   Left bound
     * @param top    Top bound
     * @param right  Right bound
     * @param bottom Bottom bound
     * @return A {@link android.graphics.Path} corresponding to a circle.
     */
    public static Path circle(int left, int top, int right, int bottom) {
        if (right - left != bottom - top) {
            throw new IllegalArgumentException("Provided bounds (" + left + ", " + top + ", " +
                    right + ", " + bottom + ") must be square.");
        }

        Path path = new Path();
        // sweep angle is mod 360, so we can't actually use 360.
        path.arcTo(new RectF(left, top, right, bottom), 0, 359.9999f);
        return path;
    }
}
