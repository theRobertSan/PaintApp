package com.example.paint;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int backgroundColor = ((Global) getApplicationContext()).getBackgroundColor();

        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.setBackgroundResource(backgroundColor);

    }

    // Add Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.extra_options_menu, menu);
        return true;
    }

    // Show options when option is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_option:
                showSettings();
                return true;
            case R.id.about_option:
                showAbout();
                return true;
            case R.id.maps_option:
                showMaps();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showSettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void showAbout() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void showMaps() {
        Intent intent = new Intent(this, Maps.class);
        startActivity(intent);
    }
}