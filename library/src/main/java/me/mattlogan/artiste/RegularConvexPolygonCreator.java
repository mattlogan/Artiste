package me.mattlogan.artiste;

import android.graphics.Path;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Created by Matt Logan on 2/22/15.
 */
class RegularConvexPolygonCreator {

    static Path createRegularConvexPolygon(int left, int top, int right, int bottom,
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
}
