package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner spinner = (Spinner) findViewById(R.id.background_color_selector);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.background_colors_array, R.layout.spinner_item);
        spinner.setAdapter(adapter);

        String backgroundColorName = ((Global)getApplicationContext()).getBackgroundColorName();
        int spinnerPosition = adapter.getPosition(backgroundColorName);
        spinner.setSelection(spinnerPosition);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String backgroundColor = adapterView.getItemAtPosition(i).toString();

        // Save selected background color in Global
        ((Global)getApplicationContext()).setBackgroundColorName(backgroundColor);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}