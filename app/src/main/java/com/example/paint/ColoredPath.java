package com.example.paint;

import android.graphics.Paint;
import android.graphics.Path;

public class ColoredPath {
    private Paint paint;
    private Path path;

    public ColoredPath(Paint paint, Path path) {
        this.paint = paint;
        this.path = path;
    }

    public Paint getPaint() {
        return paint;
    }

    public Path getPath() {
        return path;
    }
}