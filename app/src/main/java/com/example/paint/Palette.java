package com.example.paint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.apandroid.colorwheel.ColorWheel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Palette extends Fragment {

    private BrushViewModel viewModel;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://paint-d2783-default-rtdb.europe-west1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("canvas");

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BrushViewModel.class);
        ColorWheel colorWheel = view.findViewById(R.id.colorWheel);
        colorWheel.setColorChangeListener(color -> {
            viewModel.setColor(color);
            return null;
        });

        // Undo button
        view.findViewById(R.id.undo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getPaintCanvas().getValue().undo();
            }
        });


        // Save button
        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.removeValue();
                myRef.setValue(viewModel.getPaintCanvas().getValue().createDataObject());
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Recover button
                view.findViewById(R.id.recover_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.getPaintCanvas().getValue().loadDataObject(snapshot.getValue(CanvasDataObject.class));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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