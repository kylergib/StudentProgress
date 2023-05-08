package com.kgibs87.studentprogress.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.fragment.DateFragment;
import com.kgibs87.studentprogress.fragment.FloatingButtonFragment;
import com.kgibs87.studentprogress.model.Assessment;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddAssessmentActivity extends AppCompatActivity implements DateFragment.OnDateSelectedListener, FloatingButtonFragment.OnButtonClickListener {
    private EditText assessmentNameEditText;
    private LocalDate startDate;
    private LocalDate endDate;
    private String assessmentType;
    private static Assessment currentAssessment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        if (currentAssessment == null) {
            startDate = LocalDate.now();
            endDate = LocalDate.now();
        }
        assessmentNameEditText = findViewById(R.id.assessmentNameEditText);
        if (currentAssessment == null)
            currentAssessment = new Assessment();

        String[] statusList = {"Performance", "Objective"};
        Spinner spinner = findViewById(R.id.assessmentTypeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selected item here
                assessmentType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing here
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment dateFragmentStart = fragmentManager.findFragmentById(R.id.startDateFragmentContainer);
        if (dateFragmentStart == null) {
            String tag = "startDate";
            dateFragmentStart = new DateFragment(tag);
            fragmentManager.beginTransaction()
                    .add(R.id.startDateFragmentContainer, dateFragmentStart,tag)
                    .commit();
        }

        Fragment dateFragmentEnd = fragmentManager.findFragmentById(R.id.endDateFragmentContainer);
        if (dateFragmentEnd == null) {
            String endTag = "endDate";
            dateFragmentEnd = new DateFragment(endTag);


            fragmentManager.beginTransaction()
                    .add(R.id.endDateFragmentContainer, dateFragmentEnd,endTag)
                    .commit();
        }

        Fragment addButtonFragment = fragmentManager.findFragmentById(R.id.addButtonFragmentContainer);
        if (addButtonFragment == null) {
            String saveTag = "saveAssessmentButton";
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.END | Gravity.BOTTOM;
            addButtonFragment = new FloatingButtonFragment(saveTag,params, R.drawable.baseline_check);

            fragmentManager.beginTransaction()
                    .add(R.id.addButtonFragmentContainer, addButtonFragment,saveTag)
                    .commit();
        }

        Fragment backButtonFragment = fragmentManager.findFragmentById(R.id.backButtonFragmentContainer);
        if (backButtonFragment == null) {
            String cancelTag = "cancelAssessmentButton";
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.START | Gravity.BOTTOM;
            backButtonFragment = new FloatingButtonFragment(cancelTag,params,R.drawable.baseline_close);
            fragmentManager.beginTransaction()
                    .add(R.id.addButtonFragmentContainer, backButtonFragment,cancelTag)
                    .commit();
        }


    }

//    public void cancelAssessmentClick(View view) {
//        finish();
//    }
//    public void addAssessmentClick(View view) {
//        //TODO: add logic to add assessment to database
//        finish();
//    }

    @Override
    public void onDateSelected(LocalDate localDate, String tag) {
        // Check the tag to determine which DateFragment is invoking the method
        if (tag.equals("startDate")) {
//            startDate = date;
            Log.d("AddAssessmentActivity", "Start date selected: " + localDate.toString());
        } else if (tag.equals("endDate")) {
//            endDate = date;
            Log.d("AddAssessmentActivity", "End date selected: " + localDate.toString());
        }
    }

    @Override
    public void onButtonClick(View view, String tag) {
        if (tag.equals("cancelAssessmentButton")) {
            Log.d("Back tag", tag);
            finish();
        } else if (tag.equals("saveAssessmentButton")) {
            Log.d("Add tag", tag);
            //TODO: create term object and add to sqlite
            Intent returnIntent = new Intent();
            String assessmentName = String.valueOf(assessmentNameEditText.getText());
            Assessment newAssessment = new Assessment(assessmentName,startDate,endDate,assessmentType);
            AddCourseActivity.currentCourse.addCourseAssessment(newAssessment);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        currentAssessment = null;
    }
}