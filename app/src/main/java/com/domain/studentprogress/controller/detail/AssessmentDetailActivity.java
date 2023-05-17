package com.domain.studentprogress.controller.detail;

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
import android.widget.TextView;

import com.domain.studentprogress.fragment.DateFragment;
import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.model.Assessment;
import com.kgibs87.studentprogress.R;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.HashMap;

public class AssessmentDetailActivity  extends AppCompatActivity implements DateFragment.OnDateSelectedListener, FloatingButtonFragment.OnButtonClickListener {
    private EditText assessmentNameEditText;
    private LocalDate startDate;
    private LocalDate endDate;
    private String assessmentType;
    private static Assessment currentAssessment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);


        Intent intent = getIntent();

        boolean intentHasAssessment = intent.hasExtra("currentAssessment");
        if (intentHasAssessment) {
            currentAssessment = (Assessment) intent.getSerializableExtra("currentAssessment");
            TextView headerText = findViewById(R.id.headerTitle);
            headerText.setText(currentAssessment.getAssessmentTitle());
            TextView assessmentNameTextView = findViewById(R.id.assessmentNameView);
            assessmentNameTextView.setVisibility(View.GONE);

            //TODO: does this need to be a class variable?
            assessmentNameEditText = findViewById(R.id.assessmentNameEditText);
            assessmentNameEditText.setVisibility(View.GONE);

            TextView startView = findViewById(R.id.startDate);
            startView.setVisibility(View.GONE);

            View startEditText = findViewById(R.id.startDateFragmentContainer);
            startEditText.setVisibility(View.GONE);




            TextView endView = findViewById(R.id.endDate);
            endView.setVisibility(View.GONE);

            View endEditText = findViewById(R.id.endDateFragmentContainer);
            endEditText.setVisibility(View.GONE);
            TextView typeTextView = findViewById(R.id.assessmentTypeView);
            typeTextView.setVisibility(View.GONE);
            Spinner spinner = findViewById(R.id.assessmentTypeSpinner);
            spinner.setVisibility(View.GONE);




        }
        else {
            startDate = LocalDate.now();
            endDate = LocalDate.now();
            currentAssessment = new Assessment();
            assessmentNameEditText = findViewById(R.id.assessmentNameEditText);
            addAssessmentSetup();
        }



        FragmentManager fragmentManager = getSupportFragmentManager();

        //TODO: change logo of back button
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

    public void addAssessmentSetup() {

        String[] statusList = {"Performance", "Objective"};
        HashMap<String,Integer> statusMap = new HashMap<String,Integer>() {{
            put("Performance",0);
            put("Objective",1);
        }};

        Spinner spinner = findViewById(R.id.assessmentTypeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
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



    }

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
            CourseDetailsActivity.currentCourse.addCourseAssessment(newAssessment);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        currentAssessment = null;
    }
}