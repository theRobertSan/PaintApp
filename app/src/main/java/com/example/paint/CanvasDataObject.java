package com.example.paint;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class CanvasDataObject {

    private int backgroundColor;
    private List<ArrayList<Action>> paths;
    private List<Integer> colors;

    public CanvasDataObject(int backgroundColor, List<ArrayList<Action>> paths, List<Integer> colors) {
        this.backgroundColor = backgroundColor;
        this.paths = paths;
        this.colors = colors;
    }

    public CanvasDataObject() {
        
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public List<ArrayList<Action>> getPaths() {
        return paths;
    }

    public List<Integer> getColors() {
        return colors;
    }
}
