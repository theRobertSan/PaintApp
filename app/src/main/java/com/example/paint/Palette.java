package com.example.paint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.apandroid.colorwheel.ColorWheel;

public class Palette extends Fragment {

    private BrushViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BrushViewModel.class);
        ColorWheel colorWheel = view.findViewById(R.id.colorWheel);
        colorWheel.setColorChangeListener(color -> {
            viewModel.setColor(color);
            return null;
        });

        ((Button) view.findViewById(R.id.undo_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getPaintCanvas().getValue().undo();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_palette, container, false);
    }
}