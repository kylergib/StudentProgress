package com.kgibs87.studentprogress.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kgibs87.studentprogress.R;

public class AddTermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
    }

    public void addCourseClick(View view) {
        Log.d("Debug-teg", "addcourseclicked");
        Intent courseIntent = new Intent(getApplicationContext(), AddCourseActivity.class);
        startActivity(courseIntent);
    }

    public void cancelTermClick(View view) {
        Log.d("Debug-teg", "canceltermclicekd");
        finish();
    }

    public void addTermClick(View view) {
        Log.d("Debug-teg", "addTermClick");

        //TODO: create term object and add to sqlite

        finish();
    }
}