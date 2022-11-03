package com.example.paint;

import android.graphics.Path;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class CustomPath extends Path implements Serializable {

    private static final long serialVersionUID = -5974912367682897467L;

    public ArrayList<Action> actions = new ArrayList<>();

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        drawThisPath();
    }

    @Override
    public void moveTo(float x, float y) {
        actions.add(new Action(x, y, "MOVE_TO"));
        super.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y) {
        actions.add(new Action(x, y, "LINE_TO"));
        super.lineTo(x, y);
    }

    public void drawThisPath() {
        for (Action p : actions) {
            if (p.getType().equals("MOVE_TO")) {
                super.moveTo(p.getX(), p.getY());
            } else if (p.getType().equals("LINE_TO")) {
                super.lineTo(p.getX(), p.getY());
            }
        }
    }


}