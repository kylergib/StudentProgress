package com.kgibs87.studentprogress.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.kgibs87.studentprogress.R;

public class AddCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
    }
    public void addAssessmentClick(View view) {

    }
    public void addInstructorClick(View view) {

    }
    public void addNoteClick(View view) {

    }
    public void cancelCourseClick(View view) {
        finish();
    }
    public void addCourseClick(View view) {
        //TODO: add course to database
        finish();
    }
}