package com.kgibs87.studentprogress.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kgibs87.studentprogress.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    String debugTag = "Debug";

    private EditText nameEditText;

    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor sharedPrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getSharedPreferences("preferences",Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();

        if (sharedPref.getString("name",null) == null) {
            setContentView(R.layout.activity_main);
            nameEditText = findViewById(R.id.nameEditText);
        } else {
            startDashboard();
        }





    }

    public void submitName(View view) {
        //get text in name edit text
        String nameEntered = nameEditText.getText().toString();
        if (nameEntered.equals("")) {
            Toast.makeText(MainActivity.this, "Name cannot be blank.", Toast.LENGTH_LONG).show();
            return;
        }
        sharedPrefEditor.putString("name", nameEntered);
        sharedPrefEditor.apply();
        startDashboard();
    }

    public void startDashboard() {
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        startActivity(dashboardIntent);
    }


}