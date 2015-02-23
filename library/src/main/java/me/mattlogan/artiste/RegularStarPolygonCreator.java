package me.mattlogan.artiste;

import android.graphics.Path;

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
class RegularStarPolygonCreator {

    static Path createRegularStarPolygon(int left, int top, int right, int bottom,
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
}
