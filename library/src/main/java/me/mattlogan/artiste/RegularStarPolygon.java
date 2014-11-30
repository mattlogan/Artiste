package me.mattlogan.artiste;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public abstract class RegularStarPolygon extends Shape {

    Path path;

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

        float degreesBetweenPoints = 360f / numPoints;

        float startDegrees = 90;

        int density = getDensity();

        path = new Path();

        for (int i = 0; i <= numPoints; i++) {
            double theta = toRadians(startDegrees + density * i * degreesBetweenPoints);

            float x = (float) (r + (r * cos(theta)));
            float y = (float) (r - (r * sin(theta)));

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (path == null) {
            throw new IllegalStateException("setBounds() must be called before getPath()");
        }
        canvas.drawPath(path, paint);
    }

    public abstract int getNumberOfPoints();

    // The density of a star polygon is the number of points to skip when drawing a line
    // connecting two of its points. For example, a line in a five-pointed star connects
    // the first and third points, so its density is two.
    public abstract int getDensity();
}
