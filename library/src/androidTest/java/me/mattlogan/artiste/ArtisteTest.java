package me.mattlogan.artiste;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ArtisteTest extends MockitoUnitTestCase {

    @Mock Shape shape;
    @Mock Paint paint;
    @Mock Canvas canvas;
    Rect rect; // final classes can't be mocked

    Artiste artiste;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        rect = new Rect();
        artiste = new Artiste();
    }

    public void testDrawShape() {
        artiste.drawShape(shape);
        assertEquals(shape, artiste.shape);
    }

    public void testWithPaint() {
        artiste.withPaint(paint);
        assertEquals(paint, artiste.paint);
    }

    public void testOnCanvasThrowsExceptionIfShapeOrPaintNotSet() {
        artiste.shape = shape;
        try {
            artiste.onCanvas(canvas);
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // success
        }
    }

    public void testOnCanvas() {
        artiste.shape = shape;
        artiste.paint = paint;
        artiste.shape.setBounds(rect);

        artiste.onCanvas(canvas);

        verify(shape, times(1)).draw(canvas, paint);
    }

    public void testClearAll() {
        artiste.shape = shape;
        artiste.paint = paint;

        artiste.clearAll();

        assertNull(artiste.shape);
        assertNull(artiste.paint);
    }
}
