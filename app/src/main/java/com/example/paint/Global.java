package com.example.paint;

import android.app.Application;
import android.content.res.Resources;

import java.util.HashMap;

public class Global extends Application {

    HashMap<String, Integer> colors = new HashMap<String, Integer>() {{
        put("White", R.color.white);
        put("Light Red", R.color.light_red);
        put("Light Blue", R.color.light_blue);
        put("Light Green", R.color.light_green);
    }};

    private String backgroundColorName = "White";

    public String getBackgroundColorName() {
        return backgroundColorName;
    }

    public int getBackgroundColor() {
        return colors.get(backgroundColorName);
    }

    public void setBackgroundColorName(String backgroundColorName) {
        this.backgroundColorName = backgroundColorName;
    }
}
