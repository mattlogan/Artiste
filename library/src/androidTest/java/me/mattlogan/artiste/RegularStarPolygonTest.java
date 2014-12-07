package me.mattlogan.artiste;

import android.graphics.Rect;

import junit.framework.TestCase;

public class RegularStarPolygonTest extends TestCase {

    RegularStarPolygon regularStarPolygon;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        regularStarPolygon = new Shapes.FivePointedStar();
    }

    public void testCalculatePathWithSquareRectCreatesPath() {
        regularStarPolygon.calculatePath(new Rect(0, 0, 100, 100), 0);
        assertNotNull(regularStarPolygon.getPath());
    }

    public void testCalculatePathWithNonSquareRectThrowsException() {
        try {
            regularStarPolygon.calculatePath(new Rect(0, 0, 99, 100), 0);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
        assertNull(regularStarPolygon.path);
    }

    public void testGetPathThrowsExceptionIfPathNotSet() {
        try {
            regularStarPolygon.getPath();
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
    }
}
