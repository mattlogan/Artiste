package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.RectF;

import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static me.mattlogan.artiste.LineMath.distance;
import static me.mattlogan.artiste.LineMath.slope;
import static me.mattlogan.artiste.LineMath.yIntercept;

/**
 * Created by Matt Logan on 2/22/15.
 */
public class Artiste {

    /**
     * Creates a regular convex polygon {@link android.graphics.Path}.
     *
     * @param left     Left bound
     * @param top      Top bound
     * @param right    Right bound
     * @param bottom   Bottom bound
     * @param numSides Number of sides
     * @return A {@link android.graphics.Path} corresponding to a regular convex polygon. Uses
     * rotation value of 0.
     */
    public static Path createRegularConvexPolygon(int left, int top, int right, int bottom,
                                                  int numSides) {

        return createRegularConvexPolygon(left, top, right, bottom, numSides, 0);
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
    public static Path createRegularConvexPolygon(int left, int top, int right, int bottom,
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
     * @param left      Left bound
     * @param top       Top bound
     * @param right     Right bound
     * @param bottom    Bottom bound
     * @param numPoints Number of points on star
     * @param density   Density of the star polygon (the number of vertices, or points, to skip when
     *                  drawing a line connecting two vertices.)
     * @return A {@link android.graphics.Path} corresponding to a regular star polygon. Uses a
     * rotation value of 0, and draws only the outline by default.
     */
    public static Path createRegularStarPolygon(int left, int top, int right, int bottom,
                                                int numPoints, int density) {

        return createRegularStarPolygon(left, top, right, bottom, numPoints, density, 0, true);
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
     * @return A {@link android.graphics.Path} corresponding to a regular star polygon. Draws only
     * the outline by default.
     */
    public static Path createRegularStarPolygon(int left, int top, int right, int bottom,
                                                int numPoints, int density, float rotationDegrees) {

        return createRegularStarPolygon(left, top, right, bottom, numPoints, density,
                rotationDegrees, true);
    }

    /**
     * Creates a regular star polygon {@link android.graphics.Path}.
     *
     * @param left      Left bound
     * @param top       Top bound
     * @param right     Right bound
     * @param bottom    Bottom bound
     * @param numPoints Number of points on star
     * @param density   Density of the star polygon (the number of vertices, or points, to
     *                  skip when drawing a line connecting two vertices.)
     * @param outline   True if only the star's outline should be drawn. If false, complete lines
     *                  will be drawn connecting the star's vertices.
     * @return A {@link android.graphics.Path} corresponding to a regular star polygon. Uses a
     * rotation value of 0.
     */
    public static Path createRegularStarPolygon(int left, int top, int right, int bottom,
                                                int numPoints, int density, boolean outline) {

        return createRegularStarPolygon(left, top, right, bottom, numPoints, density, 0, outline);
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
    public static Path createRegularStarPolygon(int left, int top, int right, int bottom,
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

    private static float[][] makeOuterPointsArray(int numPoints, int density, float startDegrees,
                                                  float r) {

        float degreesBetweenPoints = 360f / numPoints;
        float[][] outerPoints = new float[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            double theta = toRadians(startDegrees + density * i * degreesBetweenPoints);
            outerPoints[i][0] = (float) (r + (r * cos(theta)));
            outerPoints[i][1] = (float) (r - (r * sin(theta)));
        }
        return outerPoints;
    }

    private static float[] findFirstIntersectionPoint(float[][] pointsArray) {
        float[] firstPt1 = pointsArray[0];
        float[] firstPt2 = pointsArray[1];

        float firstSlope = slope(firstPt1, firstPt2);
        float firstYInt = yIntercept(firstPt1, firstSlope);

        // Ranges for first line. We'll use these later to check if the intersection we find
        // is in the valid range.
        float firstLowX = min(firstPt1[0], firstPt2[0]);
        float firstHighX = max(firstPt1[0], firstPt2[0]);
        float firstLowY = min(firstPt1[0], firstPt2[1]);
        float firstHighY = max(firstPt1[1], firstPt2[1]);

        // The second line and the last line can't intersect the first line. Skip them.
        for (int i = 2; i < pointsArray.length - 1; i++) {
            float[] curPt1 = pointsArray[i];
            float[] curPt2 = pointsArray[i + 1];

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
                return new float[]{intersectionX, intersectionY};
            }
        }

        // If there are no intersections, it's not a star polygon.
        throw new IllegalStateException("Failed to calculate path." +
                "Are the number of points and density valid?");
    }

    private static float[][] makeOutlinePointsArray(int numPoints, float startDegrees,
                                                    float outerRadius, float innerRadius) {

        float degreesBetweenPoints = 360f / numPoints;
        float[][] outlinePoints = new float[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            double theta = toRadians(startDegrees + i * degreesBetweenPoints);

            float radius = i % 2 == 0 ? outerRadius : innerRadius;

            outlinePoints[i][0] = (float) (outerRadius + (radius * cos(theta)));
            outlinePoints[i][1] = (float) (outerRadius - (radius * sin(theta)));
        }

        return outlinePoints;
    }

    private static Path createStarPathFromPointsArray(float xOffset, float yOffset,
                                                      float[][] pointsArray) {

        Path path = new Path();
        for (int i = 0; i < pointsArray.length; i++) {
            float x = xOffset + pointsArray[i][0];
            float y = yOffset + pointsArray[i][1];

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.lineTo(xOffset + pointsArray[0][0], yOffset + pointsArray[0][1]);

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
    public static Path createCircle(int left, int top, int right, int bottom) {
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
