package com.kgibs87.studentprogress.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.model.StudentDatabase;

public class DashboardActivity extends AppCompatActivity {

    private static StudentDatabase mStudentDb ;
    public static SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_dashboard);


        TextView welcomeText = findViewById(R.id.welcomeText);
        String welcomeMessage = getResources().getString(R.string.welcome_text);
        welcomeText.setText(String.format(welcomeMessage, sharedPref.getString("name",null)));
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
    }

    public void addTermClick(View view) {
        Log.d("Debug","Add term clicked");
        //TODO: start add term activity
    }
}