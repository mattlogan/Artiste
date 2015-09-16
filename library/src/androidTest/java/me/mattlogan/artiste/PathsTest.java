package me.mattlogan.artiste;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class PathsTest {

    @Test
    public void testCircle() {
        Path circle = Paths.circle(0, 0, 100, 100);
        PathMeasure pm = new PathMeasure(circle, false);
        assertEquals(314, pm.getLength(), 1);
    }
}
