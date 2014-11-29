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
