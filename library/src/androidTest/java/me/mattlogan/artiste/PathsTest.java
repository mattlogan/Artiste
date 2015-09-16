package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * It's hard to be 100% sure, even with these tests, that the static factory methods in Paths
 * are returning Paths with the correct shape. Some of these tests are a bit more implicit -- for
 * example, making sure a pentagon has a larger perimeter than a square. Others just check for
 * known values -- for example, that the perimeter of a square inscribed inside a circle with
 * diameter 100 is 282.84.
 */
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
    public void testRegularConvexPolygonPerimeter() {
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

    /*
    Num sides less than 3 should throw.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegularConvexPolygonThrowsWithLessThanThreeSides() {
        Paths.regularConvexPolygon(0, 0, 100, 99, 2, 0);
    }

    /*
    Check the perimeter of an outlined regular convex polygon.
     */
    @Test
    public void testRegularStarPolygonOutlinePerimeter() {
        Path path = Paths.regularStarPolygon(0, 0, 100, 100, 5, 2, 0, true);
        PathMeasure pm = new PathMeasure(path, false);

        // Perimeter of outlined five pointed star inscribed in circle with diameter 100
        float expectedPerimeter = 363.27f;

        assertEquals(expectedPerimeter, pm.getLength(), 0.01f);
    }

    /*
    Check the perimeter of a regular convex polygon without outline.
     */
    @Test
    public void testRegularStarPolygonNoOutlinePerimeter() {
        Path path = Paths.regularStarPolygon(0, 0, 100, 100, 5, 2, 0, false);
        PathMeasure pm = new PathMeasure(path, false);

        // Perimeter of non-outlined five pointed star inscribed in circle with diameter 100
        float expectedPerimeter = 475.53f;

        assertEquals(expectedPerimeter, pm.getLength(), 0.01f);
    }

    /*
    Make sure that rotation has no effect on the perimeter of a regular star polygon.
     */
    @Test
    public void testRegularStarPolygonOutlinePerimeterIsRotationIndependent() {
        Path path1 = Paths.regularStarPolygon(0, 0, 100, 100, 5, 2, 0, true);
        PathMeasure pm1 = new PathMeasure(path1, false);

        Path path2 = Paths.regularStarPolygon(0, 0, 100, 100, 5, 2, 60, true);
        PathMeasure pm2 = new PathMeasure(path2, false);

        assertEquals(pm1.getLength(), pm2.getLength(), 0);
    }

    /*
    Make sure that rotation has no effect on the perimeter of a regular star polygon.
     */
    @Test
    public void testRegularStarPolygonNoOutlinePerimeterIsRotationIndependent() {
        Path path1 = Paths.regularStarPolygon(0, 0, 100, 100, 5, 2, 0, false);
        PathMeasure pm1 = new PathMeasure(path1, false);

        Path path2 = Paths.regularStarPolygon(0, 0, 100, 100, 5, 2, 60, false);
        PathMeasure pm2 = new PathMeasure(path2, false);

        assertEquals(pm1.getLength(), pm2.getLength(), 0);
    }

    /*
    Check relative magnitudes of regular star polygon perimeters. For example, an eight pointed
    star should have a larger perimeter than a five pointed star if both are inscribed in the same
    circle.
     */
    @Test
    public void testRelativePerimetersOfRegularStarPolygons() {
        Path path1 = Paths.regularStarPolygon(0, 0, 100, 100, 5, 2, 0, true);
        PathMeasure pm1 = new PathMeasure(path1, false);

        Path path2 = Paths.regularStarPolygon(0, 0, 100, 100, 8, 3, 0, true);
        PathMeasure pm2 = new PathMeasure(path2, false);

        assertTrue(pm2.getLength() > pm1.getLength());
    }

    /*
    Non-square bounds should throw.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegularStarPolygonThrowsWithNonSquareBounds() {
        Paths.regularStarPolygon(0, 0, 100, 99, 5, 2, 0, true);
    }

    /*
    Num points less than 5 should throw.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegularStarPolygonThrowsWithLessThanThreePoints() {
        Paths.regularStarPolygon(0, 0, 100, 100, 4, 2, 0, true);
    }

    /*
    Density less than 2 should throw.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegularStarPolygonThrowsWithDensityLessThanTwo() {
        Paths.regularStarPolygon(0, 0, 100, 100, 5, 1, 0, true);
    }

    /*
    While a six pointed star can exist, it can't be drawn with one continuous line, so it's
    outside the scope of this project (for now).
     */
    @Test(expected = IllegalStateException.class)
    public void testRegularStarPolygonThrowsForInvalidShape() {
        Paths.regularStarPolygon(0, 0, 100, 100, 6, 2, 0, true);
    }
}
