package me.mattlogan.artiste;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RegularConvexPolygonTest extends MockitoUnitTestCase {

    @Mock Canvas canvas;
    @Mock Paint paint;

    RegularConvexPolygon regularConvexPolygon;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        regularConvexPolygon = new Shapes.Pentagon();
    }

    public void testSetRotationThrowsExceptionIfPathAlreadySet() {
        regularConvexPolygon.path = new Path();
        try {
            regularConvexPolygon.setRotation(0);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
    }

    public void testSetBoundsWithSquareRectCreatesPath() {
        regularConvexPolygon.calculatePath(new Rect(0, 0, 100, 100));
        assertNotNull(regularConvexPolygon.path);
    }

    public void testSetBoundsWithNonSquareRectThrowsException() {
        try {
            regularConvexPolygon.calculatePath(new Rect(0, 0, 99, 100));
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
        assertNull(regularConvexPolygon.path);
    }

    public void testDrawThrowsExceptionIfPathNotSet() {
        try {
            regularConvexPolygon.draw(canvas, paint);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
    }

    public void testDrawCallsDrawPath() {
        regularConvexPolygon.path = new Path();
        regularConvexPolygon.draw(canvas, paint);
        verify(canvas, times(1)).drawPath(regularConvexPolygon.path, paint);
    }
}
