package com.example.paint;

import android.graphics.Paint;

public class ColoredPath {
    private Paint paint;
    private CustomPath path;

    public ColoredPath(Paint paint, CustomPath path) {
        this.paint = paint;
        this.path = path;
    }

    public Paint getPaint() {
        return paint;
    }

    public CustomPath getPath() {
        return path;
    }
}