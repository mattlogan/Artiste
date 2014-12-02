package me.mattlogan.artiste;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

public abstract class RegularStarPolygon extends Shape {

    Path path;
    boolean outlined;
    int rotationDegrees;

    private static final int START_DEGREES = 90;

    public void setOutlined(boolean outlined) {
        if (path != null) {
            throw new IllegalStateException("setOutlined() must be called before setBounds()");
        }
        this.outlined = outlined;
    }

    @Override
    public void setRotation(int rotationDegrees) {
        if (path != null) {
            throw new IllegalStateException("setRotationDegrees() must be called before setBounds()");
        }
        this.rotationDegrees = rotationDegrees;
    }

    @Override
    public void setBounds(Rect rect) {
        if (rect.width() != rect.height()) {
            throw new IllegalStateException("rect must be square");
        }

        float r = rect.width() / 2f;

        int numPoints = getNumberOfPoints();
        if (numPoints < 5) {
            throw new IllegalStateException("number of points must be at least 5");
        }

        int density = getDensity();
        if (density < 2) {
            throw new IllegalStateException("density must be at least 2");
        }

        // Add 90 so first point is top
        int startDegrees = 90 + rotationDegrees;

        float[][] outerPointsArray = makeOuterPointsArray(numPoints, density, startDegrees, r);

        if (outlined) {
            // Find the first intersection point created by drawing each line in the star
            float[] firstIntersection = findFirstIntersectionPoint(outerPointsArray);

            // Use the first intersection point to find the radius of the inner circle of the star
            float innerRadius = distance(r, r, firstIntersection[0], firstIntersection[1]);

            // Make the array of each point in the star outline
            float[][] outlinePointsArray = makeOutlinePointsArray(numPoints * 2, startDegrees, r, innerRadius);

            createPath(outlinePointsArray);
        } else {
            createPath(outerPointsArray);
        }
    }

    private float[][] makeOuterPointsArray(int numPoints, int density, float startDegrees, float r) {
        float degreesBetweenPoints = 360f / numPoints;
        float[][] outerPoints = new float[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            double theta = toRadians(startDegrees + density * i * degreesBetweenPoints);
            outerPoints[i][0] = (float) (r + (r * cos(theta)));
            outerPoints[i][1] = (float) (r - (r * sin(theta)));
        }
        return outerPoints;
    }

    // Brute force intersection finder. Step through x-values and check if y-values match.
    private float[] findFirstIntersectionPoint(float[][] pointsArray) {
        float[] firstLinePoint1 = pointsArray[0];
        float[] firstLinePoint2 = pointsArray[1];

        float firstLineSlope = slope(firstLinePoint1, firstLinePoint2);
        float firstLineYIntercept = yIntercept(firstLinePoint1, firstLineSlope);

        float firstLineLowX = firstLinePoint1[0] < firstLinePoint2[0] ? firstLinePoint1[0] : firstLinePoint2[0];
        float firstLineHighX = firstLinePoint1[0] > firstLinePoint2[0] ? firstLinePoint1[0] : firstLinePoint2[0];

        int numSteps = 1000;
        float step = distance(firstLinePoint1, firstLinePoint2) / numSteps;

        // The second line and the last line can't intersect the first line, so skip them
        for (int i = 2; i < pointsArray.length - 1; i++) {
            float[] point1 = pointsArray[i];
            float[] point2 = pointsArray[i + 1];

            float slope = slope(point1, point2);
            float yIntercept = point1[1] - slope * point1[0];

            float lowX = point1[0] < point2[0] ? point1[0] : point2[0];
            float highX = point1[0] > point2[0] ? point1[0] : point2[0];

            if (lowX > firstLineHighX || highX < firstLineLowX) {
                // lines can't intercept because x ranges don't overlap
                continue;
            }

            // find the range to check for an intersection
            float startX = max(firstLineLowX, lowX);
            float endX = min(firstLineHighX, highX);

            float minDiff = Float.MAX_VALUE;
            float[] minDiffIntersection = new float[2];

            // Step through x's in the range where the two x ranges overlap. Find the x-value with
            // the smallest difference in y-values.
            for (float x = startX; x <= endX; x += step) {
                float firstLineY = firstLineSlope * x + firstLineYIntercept;
                float y = slope * x + yIntercept;
                float dist = distance(x, firstLineY, x, y);

                if (dist < minDiff) {
                    minDiff = dist;
                    minDiffIntersection[0] = x;
                    minDiffIntersection[1] = y;
                }
            }

            // If the distance between the y-values at some x-value is smaller than a threshold
            // (we use the step value as the threshold), it's an intersection.
            if (minDiff < step) {
                return minDiffIntersection;
            }
        }

        // If there are no intersections, it's not a star polygon.
        throw new IllegalStateException("Failed to calculate path. Are the number of points and density valid?");
    }

    private float slope(float[] point1, float[] point2) {
        return (point2[1] - point1[1]) / (point2[0] - point1[0]);
    }

    private float distance(float[] point1, float[] point2) {
        return distance(point1[0], point1[1], point2[0], point2[1]);
    }

    private float distance(float x1, float y1, float x2, float y2) {
        return (float) sqrt(pow(y2 - y1, 2) + pow(x2 - x1, 2));
    }

    private float yIntercept(float[] point, float slope) {
        return point[1] - slope * point[0];
    }

    private float[][] makeOutlinePointsArray(int numPoints, float startDegrees, float outerRadius, float innerRadius) {
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

    private void createPath(float[][] pointsArray) {
        path = new Path();
        for (int i = 0; i < pointsArray.length; i++) {
            float x = pointsArray[i][0];
            float y = pointsArray[i][1];

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.lineTo(pointsArray[0][0], pointsArray[0][1]);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (path == null) {
            throw new IllegalStateException("setBounds() must be called before draw()");
        }
        canvas.drawPath(path, paint);
    }

    public abstract int getNumberOfPoints();

    // The density of a star polygon is the number of points to skip when drawing a line
    // connecting two of its points. For example, a line in a five-pointed star connects
    // the first and third points, so its density is two.
    public abstract int getDensity();
}
