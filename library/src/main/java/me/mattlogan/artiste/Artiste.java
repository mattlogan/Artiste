package me.mattlogan.artiste;

import android.graphics.Path;

/**
 * Created by Matt Logan on 2/22/15.
 */
public class Artiste {

    /**
     * Creates a regular convex polygon {@link android.graphics.Path}.
     *
     * @param left     Left bound
     * @param top      Top bound
     * @param right    Right bound
     * @param bottom   Bottom bound
     * @param numSides Number of sides
     * @return A {@link android.graphics.Path} corresponding to a regular convex polygon. Uses
     * rotation value of 0.
     */
    public static Path createRegularConvexPolygon(int left, int top, int right, int bottom,
                                                  int numSides) {

        return RegularConvexPolygonCreator.createRegularConvexPolygon(left, top, right, bottom,
                numSides, 0);
    }

    /**
     * Creates a regular convex polygon {@link android.graphics.Path}.
     *
     * @param left            Left bound
     * @param top             Top bound
     * @param right           Right bound
     * @param bottom          Bottom bound
     * @param numSides        Number of sides
     * @param rotationDegrees Degrees to rotate polygon
     * @return A {@link android.graphics.Path} corresponding to a regular convex polygon.
     */
    public static Path createRegularConvexPolygon(int left, int top, int right, int bottom,
                                                  int numSides, float rotationDegrees) {

        return RegularConvexPolygonCreator.createRegularConvexPolygon(left, top, right, bottom,
                numSides, rotationDegrees);
    }

    /**
     * Creates a regular star polygon {@link android.graphics.Path}.
     *
     * @param left      Left bound
     * @param top       Top bound
     * @param right     Right bound
     * @param bottom    Bottom bound
     * @param numPoints Number of points on star
     * @param density   Density of the star polygon (the number of vertices, or points, to skip when
     *                  drawing a line connecting two vertices.)
     * @return A {@link android.graphics.Path} corresponding to a regular star polygon. Uses a
     * rotation value of 0, and draws only the outline by default.
     */
    public static Path createRegularStarPolygon(int left, int top, int right, int bottom,
                                                int numPoints, int density) {

        return RegularStarPolygonCreator.createRegularStarPolygon(left, top, right, bottom,
                numPoints, density, 0, true);
    }

    /**
     * Creates a regular star polygon {@link android.graphics.Path}.
     *
     * @param left            Left bound
     * @param top             Top bound
     * @param right           Right bound
     * @param bottom          Bottom bound
     * @param numPoints       Number of points on star
     * @param density         Density of the star polygon (the number of vertices, or points, to
     *                        skip when drawing a line connecting two vertices.)
     * @param rotationDegrees Number of degrees to rotate star polygon
     * @return A {@link android.graphics.Path} corresponding to a regular star polygon. Draws only
     * the outline by default.
     */
    public static Path createRegularStarPolygon(int left, int top, int right, int bottom,
                                                int numPoints, int density, float rotationDegrees) {

        return RegularStarPolygonCreator.createRegularStarPolygon(left, top, right, bottom,
                numPoints, density, rotationDegrees, true);
    }

    /**
     * Creates a regular star polygon {@link android.graphics.Path}.
     *
     * @param left      Left bound
     * @param top       Top bound
     * @param right     Right bound
     * @param bottom    Bottom bound
     * @param numPoints Number of points on star
     * @param density   Density of the star polygon (the number of vertices, or points, to
     *                  skip when drawing a line connecting two vertices.)
     * @param outline   True if only the star's outline should be drawn. If false, complete lines
     *                  will be drawn connecting the star's vertices.
     * @return A {@link android.graphics.Path} corresponding to a regular star polygon. Uses a
     * rotation value of 0.
     */
    public static Path createRegularStarPolygon(int left, int top, int right, int bottom,
                                                int numPoints, int density, boolean outline) {

        return RegularStarPolygonCreator.createRegularStarPolygon(left, top, right, bottom,
                numPoints, density, 0, outline);
    }

    /**
     * Creates a regular star polygon {@link android.graphics.Path}.
     *
     * @param left            Left bound
     * @param top             Top bound
     * @param right           Right bound
     * @param bottom          Bottom bound
     * @param numPoints       Number of points on star
     * @param density         Density of the star polygon (the number of vertices, or points, to
     *                        skip when drawing a line connecting two vertices.)
     * @param rotationDegrees Number of degrees to rotate star polygon
     * @param outline         True if only the star's outline should be drawn. If false, complete lines
     *                        will be drawn connecting the star's vertices.
     * @return A {@link android.graphics.Path} corresponding to a regular star polygon.
     */
    public static Path createRegularStarPolygon(int left, int top, int right, int bottom,
                                                int numPoints, int density, float rotationDegrees,
                                                boolean outline) {

        return RegularStarPolygonCreator.createRegularStarPolygon(left, top, right, bottom,
                numPoints, density, rotationDegrees, outline);
    }

    /**
     * Creates a circle {@link android.graphics.Path}.
     *
     * @param left   Left bound
     * @param top    Top bound
     * @param right  Right bound
     * @param bottom Bottom bound
     * @return A {@link android.graphics.Path} corresponding to a circle.
     */
    public static Path createCircle(int left, int top, int right, int bottom) {
        return CircleCreator.createCircle(left, top, right, bottom);
    }
}
