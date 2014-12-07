package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.Rect;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public abstract class RegularConvexPolygon implements Shape {

    Path path;

    @Override
    public final void calculatePath(Rect rect, float rotationDegrees) {
        if (rect.width() != rect.height()) {
            throw new IllegalStateException("rect must be square");
        }

        float r = rect.width() / 2f;

        float xOffset = rect.left;
        float yOffset = rect.top;

        int numPoints = getNumberOfSides();
        if (numPoints < 3) {
            throw new IllegalStateException("number of sides must be at least 3");
        }

        float degreesBetweenPoints = 360f / numPoints;

        // Add 90 so first point is top
        float baseRotation = 90 + rotationDegrees;

        // Assume we want a point of the polygon at the top unless otherwise set
        float startDegrees = getNumberOfSides() % 2 != 0 ? baseRotation : baseRotation + degreesBetweenPoints / 2f;

        path = new Path();

        for (int i = 0; i <= numPoints; i++) {
            double theta = toRadians(startDegrees + i * degreesBetweenPoints);

            float x = (float) (xOffset + r + (r * cos(theta)));
            float y = (float) (yOffset + r - (r * sin(theta)));

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
    }

    @Override
    public final Path getPath() {
        if (path == null) {
            throw new IllegalStateException("calculatePath() must be called before getPath()");
        }
        return path;
    }

    public abstract int getNumberOfSides();
}
