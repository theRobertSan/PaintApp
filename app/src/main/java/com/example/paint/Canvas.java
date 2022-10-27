package com.example.paint;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class Canvas extends Fragment {

    private BrushViewModel viewModel;
    private PaintCanvas paintCanvas;
    private SensorManager sensorManager;
    private float acceleration = 0;
    private float lastAcceleration = 0;
    private float currentAcceleration = 0;
    private boolean brightened = false;

    private boolean laying = false;
    private final float LAYING_THRESHOLD = 1.5f;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getColor().observe(getViewLifecycleOwner(), color -> {
            paintCanvas.newColor(color);
        });

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        // Manage shake
        Sensor shakeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener shakeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if (sensorEvent != null) {
                    float x = sensorEvent.values[0];
                    float y = sensorEvent.values[1];
                    float z = sensorEvent.values[2];
                    lastAcceleration = currentAcceleration;

                    currentAcceleration = (float) Math.sqrt(x * x + y * y + z * z);
                    float delta = currentAcceleration - lastAcceleration;
                    acceleration = currentAcceleration * 0.9f + delta;

                    if (acceleration > 18) {
                        paintCanvas.erase();
                    }

                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        sensorManager.registerListener(shakeEventListener, shakeSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Manage brightness
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        SensorEventListener lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if (!brightened && sensorEvent.values[0] < 3) {
                    brightened = true;
                    paintCanvas.brightenUpBackground();
                } else if (brightened && sensorEvent.values[0] >= 3) {
                    brightened = false;
                    paintCanvas.brightenDownBackground();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Manage axis
        Sensor axisSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        SensorEventListener axisEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                if (!laying && y < LAYING_THRESHOLD && y > -LAYING_THRESHOLD) {
                    laying = true;
                    paintCanvas.changeBackground();
                } else if (y > LAYING_THRESHOLD || y < -LAYING_THRESHOLD) {
                    laying = false;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        sensorManager.registerListener(axisEventListener, axisSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(BrushViewModel.class);

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_canvas, container, false);
        GestureListener mGestureListener = new GestureListener();
        GestureDetector mGestureDetector = new GestureDetector(getContext(), mGestureListener);
        mGestureDetector.setIsLongpressEnabled(true);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);

        if (viewModel.getPaintCanvas().getValue() == null) {
            paintCanvas = new PaintCanvas(getContext(), null, mGestureDetector);
            viewModel.setPaintCanvas(paintCanvas);
        } else {
            paintCanvas = viewModel.getPaintCanvas().getValue();

            // Delete previous parent's reference to paintCanvas
            ((ViewGroup) paintCanvas.getParent()).removeView(paintCanvas);
        }
        mGestureListener.setCanvas(paintCanvas);

        view.addView(paintCanvas);

        return view;
    }
}