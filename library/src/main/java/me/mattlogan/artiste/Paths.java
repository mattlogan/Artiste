package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.RectF;

import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
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
     * @param outline         True if only the star's outline should be drawn. If false, complete
     *                        lines will be drawn connecting the star's vertices.
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

        float outerRadius = (right - left) / 2f;

        // Add 90 so first point is top
        float startDegrees = 90 + rotationDegrees;

        float degreesBetweenPoints = 360f / numPoints;

        float[][] outerPointsArray = new float[numPoints][2];
        for (int i = 0; i < numPoints; i++) {
            double theta = toRadians(startDegrees + density * i * degreesBetweenPoints);
            outerPointsArray[i][0] = (float) (outerRadius + (outerRadius * cos(theta)));
            outerPointsArray[i][1] = (float) (outerRadius - (outerRadius * sin(theta)));
        }

        float[][] pointsForPath;

        if (outline) {
            // Find the first intersection point created by drawing each line in the star
            float[] firstIntersection = null;

            float[] firstPt1 = outerPointsArray[0];
            float[] firstPt2 = outerPointsArray[1];

            float firstSlope = slope(firstPt1, firstPt2);
            float firstYInt = yIntercept(firstPt1, firstSlope);

            // Ranges for first line. We'll use these later to check if the intersection we find
            // is in the valid range.
            float firstLowX = min(firstPt1[0], firstPt2[0]);
            float firstHighX = max(firstPt1[0], firstPt2[0]);
            float firstLowY = min(firstPt1[0], firstPt2[1]);
            float firstHighY = max(firstPt1[1], firstPt2[1]);

            // The second line and the last line can't intersect the first line. Skip them.
            for (int i = 2; i < outerPointsArray.length - 1; i++) {
                float[] curPt1 = outerPointsArray[i];
                float[] curPt2 = outerPointsArray[i + 1];

                float curSlope = slope(curPt1, curPt2);
                float curYInt = curPt1[1] - curSlope * curPt1[0];

                // System of equations. Two equations, two unknowns.
                // y = firstSlope * x + firstYInt
                // y = curSlope * x + curYInt

                // Solve for x and y in terms of known quantities.
                // firstSlope * x + firstYInt = curSlope * x + curYInt
                // firstSlope * x - curSlope * x = curYInt - firstYInt
                // x * (firstSlope - curSlope) = (curYInt - firstYInt)
                // x = (curYInt - firstYInt) / (firstSlope - curSlope)
                // y = firstSlope * x + firstYInt

                if (firstSlope == curSlope) {
                    // lines can't intersect if they are parallel
                    continue;
                }

                float intersectionX = (curYInt - firstYInt) / (firstSlope - curSlope);
                float intersectionY = firstSlope * intersectionX + firstYInt;

                // Ranges for current line.
                float curLowX = min(curPt1[0], curPt2[0]);
                float curHighX = max(curPt1[0], curPt2[0]);
                float curLowY = min(curPt1[0], curPt2[1]);
                float curHighY = max(curPt1[1], curPt2[1]);

                // Range where intersection has to be.
                float startX = max(firstLowX, curLowX);
                float endX = min(firstHighX, curHighX);
                float startY = max(firstLowY, curLowY);
                float endY = min(firstHighY, curHighY);

                if (intersectionX > startX && intersectionX < endX &&
                        intersectionY > startY && intersectionY < endY) {
                    // Found intersection.
                    firstIntersection = new float[]{intersectionX, intersectionY};
                }
            }

            if (firstIntersection == null) {
                // If there are no intersections, it's not a star polygon.
                throw new IllegalStateException("Failed to calculate path." +
                        "Are the number of points and density valid?");
            }

            // Use the first intersection point to find the radius of the inner circle of the star
            float innerRadius = distance(outerRadius, outerRadius, firstIntersection[0],
                    firstIntersection[1]);

            // There are now twice as many points to "line to" in our path
            numPoints *= 2;

            // Recalculate degrees between points
            degreesBetweenPoints = 360f / numPoints;

            pointsForPath = new float[numPoints][2];

            for (int i = 0; i < numPoints; i++) {
                double theta = toRadians(startDegrees + i * degreesBetweenPoints);
                float radius = i % 2 == 0 ? outerRadius : innerRadius;

                pointsForPath[i][0] = (float) (outerRadius + (radius * cos(theta)));
                pointsForPath[i][1] = (float) (outerRadius - (radius * sin(theta)));
            }
        } else {
            pointsForPath = outerPointsArray;
        }

        // Make the Path from whatever points array we're using -- outline or not
        Path path = new Path();
        for (int i = 0; i < pointsForPath.length; i++) {
            float x = left + pointsForPath[i][0];
            float y = top + pointsForPath[i][1];

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.lineTo(left + pointsForPath[0][0], top + pointsForPath[0][1]);

        return path;
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
