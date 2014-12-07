package me.mattlogan.artiste;

import android.graphics.Rect;

import junit.framework.TestCase;

public class RegularConvexPolygonTest extends TestCase {

    RegularConvexPolygon regularConvexPolygon;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        regularConvexPolygon = new Shapes.Pentagon();
    }

    public void testCalculatePathWithSquareRectCreatesPath() {
        regularConvexPolygon.calculatePath(new Rect(0, 0, 100, 100), 0);
        assertNotNull(regularConvexPolygon.getPath());
    }

    public void testCalculatePathWithNonSquareRectThrowsException() {
        try {
            regularConvexPolygon.calculatePath(new Rect(0, 0, 99, 100), 0);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
        assertNull(regularConvexPolygon.path);
    }

    public void testGetPathThrowsExceptionIfPathNotSet() {
        try {
            regularConvexPolygon.getPath();
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
    }
}
