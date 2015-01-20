package me.mattlogan.artiste.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import me.mattlogan.artiste.Shapes;

public class ShapeView extends View {

    Shapes.Hexagon hexagon;
    Shapes.FivePointedStar fivePointedStar;
    Decagram decagram;
    Shapes.Circle circle;
    Octagram octagram;
    Shapes.Pentagon pentagon;

    Paint hexagonPaint;
    Paint fivePointedStarPaint;
    Paint decagramPaint;
    Paint circlePaint;
    Paint octagramPaint;
    Paint pentagonPaint;

    public ShapeView(Context context) {
        super(context);
        init();
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        hexagon = new Shapes.Hexagon();
        fivePointedStar = new Shapes.FivePointedStar();
        decagram = new Decagram();
        circle = new Shapes.Circle();
        octagram = new Octagram();
        pentagon = new Shapes.Pentagon();

        hexagonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hexagonPaint.setColor(0xFF0263ED);
        hexagonPaint.setStrokeWidth(3);
        hexagonPaint.setStyle(Paint.Style.STROKE);

        fivePointedStarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fivePointedStarPaint.setColor(0xFFD73F22);
        fivePointedStarPaint.setStrokeWidth(4);
        fivePointedStarPaint.setStyle(Paint.Style.STROKE);

        decagramPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        decagramPaint.setColor(0xFFFFB900);
        decagramPaint.setStyle(Paint.Style.FILL);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(0xFF0263ED);
        circlePaint.setStyle(Paint.Style.FILL);

        octagramPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        octagramPaint.setColor(0xFF009C55);
        octagramPaint.setStyle(Paint.Style.STROKE);
        octagramPaint.setStrokeWidth(4);

        pentagonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pentagonPaint.setColor(0xFFD73F22);
        pentagonPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        hexagon.calculatePath(new Rect(0, 0, h, h), 25);
        fivePointedStar.calculatePath(new Rect(h, 0, 2 * h, h), 12);
        decagram.calculatePath(new Rect(2 * h, 0, 3 * h, h), 18);
        circle.calculatePath(new Rect(3 * h, 0, 4 * h, h), 0);
        octagram.calculatePath(new Rect(4 * h, 0, 5 * h, h), 22.5f);
        pentagon.calculatePath(new Rect(5 * h, 0, 6 * h, h), 50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(hexagon.getPath(), hexagonPaint);
        canvas.drawPath(fivePointedStar.getPath(), fivePointedStarPaint);
        canvas.drawPath(decagram.getPath(), decagramPaint);
        canvas.drawPath(circle.getPath(), circlePaint);
        canvas.drawPath(octagram.getPath(), octagramPaint);
        canvas.drawPath(pentagon.getPath(), pentagonPaint);
    }
}
