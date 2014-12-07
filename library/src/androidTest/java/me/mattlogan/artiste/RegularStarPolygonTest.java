package me.mattlogan.artiste;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RegularStarPolygonTest extends MockitoUnitTestCase {

    @Mock Canvas canvas;
    @Mock Paint paint;

    RegularStarPolygon regularStarPolygon;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        regularStarPolygon = new Shapes.FivePointedStar();
    }

    public void testSetOutlinedThrowsExceptionIfPathAlreadySet() {
        regularStarPolygon.path = new Path();
        try {
            regularStarPolygon.setOutlined(true);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
    }

    public void testSetRotationThrowsExceptionIfPathAlreadySet() {
        regularStarPolygon.path = new Path();
        try {
            regularStarPolygon.setRotation(0);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
    }

    public void testSetBoundsWithSquareRectCreatesPath() {
        regularStarPolygon.calculatePath(new Rect(0, 0, 100, 100));
        assertNotNull(regularStarPolygon.path);
    }

    public void testSetBoundsWithNonSquareRectThrowsException() {
        try {
            regularStarPolygon.calculatePath(new Rect(0, 0, 99, 100));
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
        assertNull(regularStarPolygon.path);
    }

    public void testDrawThrowsExceptionIfPathNotSet() {
        try {
            regularStarPolygon.draw(canvas, paint);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
    }

    public void testDrawCallsDrawPath() {
        regularStarPolygon.path = new Path();
        regularStarPolygon.draw(canvas, paint);
        verify(canvas, times(1)).drawPath(regularStarPolygon.path, paint);
    }
}
