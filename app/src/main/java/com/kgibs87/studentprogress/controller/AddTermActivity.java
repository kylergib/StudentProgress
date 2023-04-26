package com.kgibs87.studentprogress.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.fragment.DateFragment;
import com.kgibs87.studentprogress.fragment.FloatingButtonFragment;
import com.kgibs87.studentprogress.model.StudentDatabase;
import com.kgibs87.studentprogress.model.Term;

import java.time.LocalDate;
import java.util.Date;

public class AddTermActivity extends AppCompatActivity implements DateFragment.OnDateSelectedListener, FloatingButtonFragment.OnButtonClickListener {

    private static StudentDatabase mStudentDb ;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
        setContentView(R.layout.activity_add_term);

        //add dateFragment
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
            String saveTag = "saveTermButton";
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
            String cancelTag = "cancelTermButton";
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

    public void addCourseClick(View view) {
        Log.d("Debug-teg", "addcourseclicked");
        Intent courseIntent = new Intent(getApplicationContext(), AddCourseActivity.class);
        startActivity(courseIntent);
    }

    @Override
    public void onDateSelected(LocalDate localDate, String tag) {
        // Check the tag to determine which DateFragment is invoking the method
        if (tag.equals("startDate")) {
//            startDate = date;
            Log.d("AddNoteActivity", "Start date selected: " + localDate.toString());
            startDate = localDate;
        } else if (tag.equals("endDate")) {
//            endDate = date;
            Log.d("AddNoteActivity", "End date selected: " + localDate.toString());
            endDate = localDate;
        }
    }

    @Override
    public void onButtonClick(View view, String tag) {

        if (tag.equals("cancelTermButton")) {
            Log.d("Back tag", tag);
            finish();
        } else if (tag.equals("saveTermButton")) {
            Log.d("Add tag", tag);
            //TODO: create term object and add to sqlite
            EditText termNameEditText = findViewById(R.id.termNameEditText);
            String termNameString = termNameEditText.getText().toString();



            boolean endBeforeStart = endDate.isBefore(startDate);
            boolean startEqualsEnd = startDate.isEqual(endDate);
            boolean termNameEmpty = (termNameString.isEmpty());




            Log.d("AddTermActivity", String.format("%s - %s - %s",termNameString,startDate.toString(),endDate.toString()));
            Log.d("AddTermActivity", String.format("%s - %s - %s",termNameString,endBeforeStart,termNameEmpty));
            if (endBeforeStart) {
                Log.d("AddTermActivity", "Start date is not before the end date.");
                return;
                //TODO: add dialog pop up
            }
            else if (startEqualsEnd) {
                Log.d("AddTermActivity", "Start date and end date cannot be the same");
                return;
                //TODO: add dialog pop up
            }

            if (termNameEmpty) {
                Log.d("AddTermActivity", "Term name cannot be empty");
                return;
                //TODO: add dialog pop up
            }

            Term newTerm = new Term(termNameString,startDate,endDate,-1);
            long termId = mStudentDb.addTerm(newTerm);
            Log.d("AddTermTest", String.valueOf(termId));
            newTerm.setId(termId);
            Intent dashboardIntent = new Intent(this, DashboardActivity.class);
            startActivity(dashboardIntent);



//            finish();


        }
    }
}