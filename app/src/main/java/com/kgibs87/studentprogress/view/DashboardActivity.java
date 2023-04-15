package com.kgibs87.studentprogress.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.model.StudentDatabase;

public class DashboardActivity extends AppCompatActivity {

    private static StudentDatabase mStudentDb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        TextView welcomeText = findViewById(R.id.welcomeText);
        String welcomeMessage = getResources().getString(R.string.welcome_text);
        welcomeText.setText(String.format(welcomeMessage, MainActivity.sharedPref.getString("name",null)));
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
    }

    public void addTermClick(View view) {
        Log.d("Debug","Add term clicked");
        //TODO: start add term activity
    }
}