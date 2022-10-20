package com.example.paint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BrushViewModel extends ViewModel {

    private MutableLiveData<Integer> color = new MutableLiveData<>();
    private MutableLiveData<PaintCanvas> paintCanvas = new MutableLiveData<>();

    public void setColor(Integer color) {
        this.color.setValue(color);
    }

    public LiveData<Integer> getColor() {
        return color;
    }

    public void setPaintCanvas(PaintCanvas paintCanvas) {
        this.paintCanvas.setValue(paintCanvas);
    }

    public LiveData<PaintCanvas> getPaintCanvas() {
        return paintCanvas;
    }
}
