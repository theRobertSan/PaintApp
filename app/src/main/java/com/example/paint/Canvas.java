package com.example.paint;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getColor().observe(getViewLifecycleOwner(), color -> {
            paintCanvas.newColor(color);
        });
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