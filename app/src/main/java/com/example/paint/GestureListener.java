package com.example.paint;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureListener extends GestureDetector.SimpleOnGestureListener implements GestureDetector.OnDoubleTapListener {

    private PaintCanvas canvas;

    public void setCanvas(PaintCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        canvas.changeBackground();
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        canvas.erase();
        return false;
    }
}
