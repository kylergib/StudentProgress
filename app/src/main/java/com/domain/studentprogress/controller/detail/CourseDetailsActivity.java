package com.domain.studentprogress.controller.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.domain.studentprogress.model.Course;
import com.kgibs87.studentprogress.R;

public class CourseDetailsActivity extends AppCompatActivity {

    public Course currentCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);
        Intent intent = getIntent();

        boolean intentHasCourse = intent.hasExtra("currentCourse");

        if (intentHasCourse) {
            currentCourse = (Course) intent.getSerializableExtra("currentCourse");
            TextView header = findViewById(R.id.headerTitleCourse);
            header.setText(currentCourse.getCourseName());
        }
    }
}
