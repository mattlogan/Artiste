package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by Matt Logan on 2/22/15.
 */
class CircleCreator {

    static Path createCircle(int left, int top, int right, int bottom) {
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
