Artiste
=======

Android library for drawing shapes on a canvas

## Usage

In a View class, create an `Artiste` and `Shape` instance.

```java
artiste = new Artiste();
shape = new Shapes.Hexagon();
```

Define the Rect in which the shape will be drawn. It must be square.

```java
@Override
protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    rect = new Rect(0, 0, w, h);
}
```

Override `onDraw()` and use your `Artiste` to draw your `Shape` with some `Paint`.

```java
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    artiste.drawShape(shape).inRect(rect).withPaint(paint).onCanvas(canvas);
}
```

## Extend it

`Triangle`, `Square`, `Pentagon`, and `Hexagon` are currently defined in the `Shapes` class.

To create a new shape, extend `RegularConvexPolygon` if your shape is a regular convex polygon. Just override `getNumberOfSides()`. For example:

```java
public static class Tridecagon extends RegularConvexPolygon {
    @Override
    public int getNumberOfSides() {
        return 13;
    }
}
```

If your shape is not a regular convex polygon, extend `Shape` and override `getPathInRect(Rect rect)`. This is a little trickier. Look at the implementation in `RegularConvexPolygon` for guidance.

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
