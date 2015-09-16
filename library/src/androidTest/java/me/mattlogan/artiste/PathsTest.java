package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class PathsTest {

    /*
    This is an easy one. In a square of side lengths 100, the diameter of the inscribed circle
    is 100. So the perimeter of our circle should be roughly 100 * 3.14 = 314.
     */
    @Test
    public void testCircle() {
        Path path = Paths.circle(0, 0, 100, 100);
        PathMeasure pm = new PathMeasure(path, false);
        assertEquals(314, pm.getLength(), 1);
    }

    /*
    Non-square bounds should throw.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCircleThrowsWithNonSquareBounds() {
        Paths.circle(0, 0, 100, 99);
    }

    /*
    Check the perimeter of a regular convex polygon.
     */
    @Test
    public void testRegularConvexPolygonSquarePerimeter() {
        Path path = Paths.regularConvexPolygon(0, 0, 100, 100, 4, 0);
        PathMeasure pm = new PathMeasure(path, false);

        // Perimeter of square inscribed in circle with diameter 100
        float expectedPerimeter = 282.84f;

        assertEquals(expectedPerimeter, pm.getLength(), 0.01f);
    }

    /*
    Make sure that rotation has no effect on the perimeter of a regular convex polygon.
     */
    @Test
    public void testRegularConvexPolygonPerimeterIsRotationIndependent() {
        Path path1 = Paths.regularConvexPolygon(0, 0, 100, 100, 5, 0);
        PathMeasure pm1 = new PathMeasure(path1, false);

        Path path2 = Paths.regularConvexPolygon(0, 0, 100, 100, 5, 60);
        PathMeasure pm2 = new PathMeasure(path2, false);

        assertEquals(pm1.getLength(), pm2.getLength(), 0);
    }

    /*
    Check relative magnitudes of regular convex polygon perimeters. For example, a pentagon should
    have a slightly larger perimeter than a square if both are inscribed in the same circle.
     */
    @Test
    public void testRelativePerimetersOfRegularConvexPolygons() {
        Path path1 = Paths.regularConvexPolygon(0, 0, 100, 100, 6, 0);
        PathMeasure pm1 = new PathMeasure(path1, false);

        Path path2 = Paths.regularConvexPolygon(0, 0, 100, 100, 7, 0);
        PathMeasure pm2 = new PathMeasure(path2, false);

        assertTrue(pm2.getLength() > pm1.getLength());
    }

    /*
    Non-square bounds should throw.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegularConvexPolygonThrowsWithNonSquareBounds() {
        Paths.regularConvexPolygon(0, 0, 100, 99, 4, 0);
    }
}
