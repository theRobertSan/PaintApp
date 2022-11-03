package com.example.paint;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Action {

    private float x, y;
    private String type;

    public Action() {
    }

    public Action(float x, float y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
