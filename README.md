[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Artiste-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1177)

Artiste
=======

Android library for drawing shapes on a canvas

## Usage

In a `View` class, create an `Artiste` and `Shape` instance.

```java
artiste = new Artiste();
hexagon = new Shapes.Hexagon();
```

Set the `Shape`'s bounds with `setBounds(Rect rect)` -- they must be square. This triggers instantiation and calculation of that `Shape`'s `Path`. Don't call this in `onDraw(Canvas canvas)`, as it is a relatively expensive operation.

```java
@Override
protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    hexagon.setBounds(new Rect(0, 0, w, h));
}
```

Override `onDraw(Canvas canvas)` and tell your `Artiste` to draw your `Shape` with some `Paint` on the `Canvas`.

```java
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    artiste.drawShape(shape).withPaint(paint).onCanvas(canvas);
}
```

## Extend it

`Triangle`, `Square`, `Pentagon`, `Hexagon`, `FivePointedStar`, and `Circle` are defined in the `Shapes` class.

To create a new shape, extend `RegularConvexPolygon` or `RegularStarPolygon` if the shape you wish to draw is either of these kinds of shapes (search for "Regular Polygon" on Wikipedia for reference). For example:

```java
public static class Tridecagon extends RegularConvexPolygon {
    @Override
    public int getNumberOfSides() {
        return 13;
    }
}

public static class Octagram extends RegularStarPolygon {
    @Override
    public int getNumberOfPoints() {
        return 8;
    }
    
    @Override
    public int getDensity() {
        return 3;
    }
}
```

*Note: the "density" of a regular star polygon is the number of vertices, or points, to skip when drawing a line connecting two vertices. For example, one line of a five-pointed star starts at the first vertex, skips the second vertex (moving CW or CCW), and connects with the third vertex. Thus, a five-pointed star has a density of two.*

If your shape is not a regular convex polygon or a regular star polygon, extend `Shape` and override `setBounds(Rect rect)` and `draw(Canvas canvas, Paint paint)`. This is a little trickier. Look at `RegularConvexPolygon`, `RegularStarPolygon`, or `Circle` for guidance.

## License

The MIT License (MIT)

Copyright (c) 2014 Matthew Logan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
