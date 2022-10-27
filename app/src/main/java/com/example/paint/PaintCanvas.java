package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.graphics.ColorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PaintCanvas extends View implements View.OnTouchListener {

    private List<ColoredPath> paths = new ArrayList<>();

    ColoredPath currentColoredPath = new ColoredPath(new Paint(), new Path());
    private int currentColor = Color.WHITE;
    private int backGroundColor = Color.rgb(255, 114, 114);
    private int brightBackGroundColor = ColorUtils.blendARGB(backGroundColor, Color.WHITE, 0.5f);
    private GestureDetector mGestureDetector;

    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);

    }

    public PaintCanvas(Context context, AttributeSet attrs, GestureDetector mGestureDetector) {
        super(context, attrs);
        this.mGestureDetector = mGestureDetector;
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (ColoredPath currentPath : paths) {
            canvas.drawPath(currentPath.getPath(), currentPath.getPaint());
        }

        // canvas.drawPath(path, paint);// draws the path with the paint
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return false; // let the event go to the rest of the listeners
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            currentColoredPath = new ColoredPath(new Paint(), new Path());
            initPaint(currentColor);
            paths.add(currentColoredPath);
        }

        Path path = currentColoredPath.getPath();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);// updates the path initial point
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);// makes a line to the point each time this event is fired
                break;
            case MotionEvent.ACTION_UP:// when you lift your finger
                performClick();
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }

    public void changeBackground() {
        Random r = new Random();
        backGroundColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        brightBackGroundColor = ColorUtils.blendARGB(backGroundColor, Color.WHITE, 0.5f);
        setBackgroundColor(backGroundColor);
    }

    public void brightenUpBackground() {
        setBackgroundColor(brightBackGroundColor);
    }

    public void brightenDownBackground() {
        setBackgroundColor(backGroundColor);
    }

    public void erase() {
        paths = new ArrayList<ColoredPath>();
        newColor(currentColor);
        invalidate();
    }

    private void initPaint(int color) {
        Paint p = currentColoredPath.getPaint();
        p.setAntiAlias(true);
        p.setStrokeWidth(20f);
        p.setColor(color);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.ROUND);
    }

    public void newColor(int color) {
        currentColor = color;
    }

    public void undo() {
        if (paths.isEmpty()) {
            return;
        }
        currentColoredPath = paths.remove(paths.size() - 1);
        System.out.println(paths);
        invalidate();
    }

}



