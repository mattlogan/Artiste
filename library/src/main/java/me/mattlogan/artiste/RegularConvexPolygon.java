package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.Rect;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public abstract class RegularConvexPolygon extends Shape {

    Path path = new Path();

    @Override
    public Path getPathInRect(Rect rect) {
        if (rect.width() != rect.height()) {
            throw new IllegalStateException("rect must be square");
        }

        float r = rect.width() / 2f;

        int numPoints = getNumberOfSides();
        if (numPoints < 3) {
            throw new IllegalStateException("number of sides must be at least 3");
        }

        float degreesBetweenPoints = 360f / numPoints;

        float startDegrees = numPoints % 2 != 0 ? 90 : 90 + degreesBetweenPoints / 2f;

        for (int i = 0; i <= numPoints; i++) {
            double theta = toRadians(startDegrees + i * degreesBetweenPoints);

            float x = (float) (r + (r * cos(theta)));
            float y = (float) (r - (r * sin(theta)));

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        return path;
    }

    public abstract int getNumberOfSides();
}
