package me.mattlogan.artiste.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import me.mattlogan.artiste.Artiste;

public class AssortedShapeView extends View {

    private Path hexagonPath;
    private Path fivePointedStarPath;
    private Path decagramPath;
    private Path circlePath;
    private Path octagramPath;
    private Path pentagonPath;

    private Paint hexagonPaint;
    private Paint fivePointedStarPaint;
    private Paint decagramPaint;
    private Paint circlePaint;
    private Paint octagramPaint;
    private Paint pentagonPaint;

    public AssortedShapeView(Context context) {
        super(context);
        initPaint();
    }

    public AssortedShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public AssortedShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
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
        hexagonPath = Artiste.createRegularConvexPolygon(0, 0, h, h, 6, 25);
        fivePointedStarPath = Artiste.createRegularStarPolygon(h, 0, 2 * h, h, 5, 2, 12);
        decagramPath = Artiste.createRegularStarPolygon(2 * h, 0, 3 * h, h, 10, 3, 18, false);
        circlePath = Artiste.createCircle(3 * h, 0, 4 * h, h);
        octagramPath = Artiste.createRegularStarPolygon(4 * h, 0, 5 * h, h, 8, 3, 22.5f, false);
        pentagonPath = Artiste.createRegularConvexPolygon(5 * h, 0, 6 * h, h, 5, 50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(hexagonPath, hexagonPaint);
        canvas.drawPath(fivePointedStarPath, fivePointedStarPaint);
        canvas.drawPath(decagramPath, decagramPaint);
        canvas.drawPath(circlePath, circlePaint);
        canvas.drawPath(octagramPath, octagramPaint);
        canvas.drawPath(pentagonPath, pentagonPaint);
    }
}
