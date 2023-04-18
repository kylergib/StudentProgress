package com.kgibs87.studentprogress.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kgibs87.studentprogress.AddInstructorActivity;
import com.kgibs87.studentprogress.AddNoteActivity;
import com.kgibs87.studentprogress.R;

import java.util.Arrays;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        List<String> statusList = Arrays.asList("in progress", "completed", "dropped", "plan to take");
        Spinner spinner = findViewById(R.id.statusSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selected item here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing here
            }
        });
    }
    public void addAssessmentClick(View view) {
        Intent assessmentIntent = new Intent(getApplicationContext(), AddAssessmentActivity.class);
        startActivity(assessmentIntent);

    }
    public void addInstructorClick(View view) {
        Intent instructorIntent = new Intent(getApplicationContext(), AddInstructorActivity.class);
        startActivity(instructorIntent);

    }
    public void addNoteClick(View view) {
        Intent noteIntent = new Intent(getApplicationContext(), AddNoteActivity.class);
        startActivity(noteIntent);
    }
    public void cancelCourseClick(View view) {
        finish();
    }
    public void addCourseClick(View view) {
        //TODO: add course to database
        finish();
    }
}