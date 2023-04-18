package com.kgibs87.studentprogress.controller;

import androidx.appcompat.app.AppCompatActivity;

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
    }

    public void cancelTermClick(View view) {
        Log.d("Debug-teg", "canceltermclicekd");
    }

    public void addTermClick(View view) {
        Log.d("Debug-teg", "addTermClick");
    }
}