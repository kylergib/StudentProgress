package com.kgibs87.studentprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("dashboardStrting","Starting dashboard activity");
        setContentView(R.layout.activity_dashboard);

        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome " + MainActivity.sharedPref.getString("name",null));
        Log.d("debugTag",MainActivity.sharedPref.getString("name",null));

    }

    public void addTermClick(View view) {
        Log.d("Debug","Add term clicked");
    }
}