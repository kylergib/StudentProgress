package com.kgibs87.studentprogress.controller.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.adapter.AssessmentAdapter;
import com.kgibs87.studentprogress.adapter.InstructorAdapter;
import com.kgibs87.studentprogress.adapter.NoteAdapter;
import com.kgibs87.studentprogress.fragment.DateFragment;
import com.kgibs87.studentprogress.fragment.FloatingButtonFragment;
import com.kgibs87.studentprogress.holder.AssessmentHolder;
import com.kgibs87.studentprogress.holder.InstructorHolder;
import com.kgibs87.studentprogress.holder.NoteHolder;
import com.kgibs87.studentprogress.model.Assessment;
import com.kgibs87.studentprogress.model.Course;
import com.kgibs87.studentprogress.model.Instructor;
import com.kgibs87.studentprogress.model.Note;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity  implements DateFragment.OnDateSelectedListener,
        FloatingButtonFragment.OnButtonClickListener, AssessmentHolder.OnAssessmentClickListener,
        InstructorHolder.OnInstrcutorClickListener, NoteHolder.OnNoteClickListener {
    private EditText courseNameEditText;
    private String courseStatus;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now();
    public static final int ASSESSMENT_REQUEST_CODE = 2345;
    public static final int INSTRUCTOR_REQUEST_CODE = 2346;
    public static final int NOTE_REQUEST_CODE = 2347;
    public static Course currentCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        if (currentCourse == null) currentCourse = new Course();

        updateAssessments();

        List<String> statusList = Arrays.asList("in progress", "completed", "dropped", "plan to take");
        Spinner spinner = findViewById(R.id.statusSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selected item here
                courseStatus = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing here
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        //add dateSpinner for start date of course
        Fragment dateFragmentStart = fragmentManager.findFragmentById(R.id.startDateFragmentContainer);
        if (dateFragmentStart == null) {
            String tag = "startDate";
            dateFragmentStart = new DateFragment(tag);
            fragmentManager.beginTransaction()
                    .add(R.id.startDateFragmentContainer, dateFragmentStart,tag)
                    .commit();
        }
        //add dateSpinner for end date of course
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
            String saveTag = "saveCourseButton";
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
            String cancelTag = "cancelCourseButton";
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
        courseNameEditText = findViewById(R.id.courseNameEditText);


    }

    public void addAssessmentClick(View view) {
        Intent assessmentIntent = new Intent(getApplicationContext(), AddAssessmentActivity.class);
        startActivityForResult(assessmentIntent,ASSESSMENT_REQUEST_CODE);

    }
    public void addInstructorClick(View view) {
        Intent instructorIntent = new Intent(getApplicationContext(), AddInstructorActivity.class);
        startActivityForResult(instructorIntent,INSTRUCTOR_REQUEST_CODE);

    }
    public void addNoteClick(View view) {
        Intent noteIntent = new Intent(getApplicationContext(), AddNoteActivity.class);
        startActivityForResult(noteIntent,NOTE_REQUEST_CODE);
    }

    public void updateAssessments() {
        RecyclerView courseRecyclerView = findViewById(R.id.assessmentsRecyclerView);

        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        courseRecyclerView.setLayoutManager(linearLayoutManager);
        courseRecyclerView.setAdapter(new AssessmentAdapter(currentCourse.getCourseAssessments(), this));
    }

    public void updateInstructors() {
        RecyclerView recyclerView = findViewById(R.id.instructorsRecyclerView);

        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new InstructorAdapter(currentCourse.getCourseInstructors(), this));
    }

    public void updateNotes() {
        RecyclerView recyclerView = findViewById(R.id.notesRecyclerView);

        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new NoteAdapter(currentCourse.getCourseNotes(), this));
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ASSESSMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            updateAssessments();
        }
        else if (requestCode == INSTRUCTOR_REQUEST_CODE && resultCode == RESULT_OK) {
            updateInstructors();
        }
        else if (requestCode == NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            updateNotes();
        }

    }
    @Override
    public void onDateSelected(LocalDate localDate, String tag) {
// Check the tag to determine which DateFragment is invoking the method
        if (tag.equals("startDate")) {
//            startDate = date;
            Log.d("AddCourseActivity", "Start date selected: " + localDate.toString());
            startDate = localDate;
        } else if (tag.equals("endDate")) {
//            endDate = date;
            Log.d("AddCourseActivity", "End date selected: " + localDate.toString());
            endDate = localDate;
        }
    }

    @Override
    public void onButtonClick(View view, String tag) {
        if (tag.equals("cancelCourseButton")) {
            Log.d("Back tag", tag);

            setResult(RESULT_CANCELED);
            finish();
        } else if (tag.equals("saveCourseButton")) {
            Log.d("Add tag", tag);
            //TODO: create term object and add to sqlite
            Intent returnIntent = new Intent();

//            returnIntent.putExtra("courseName", String.valueOf(courseNameEditText.getText()));
//            returnIntent.putExtra("startDate",startDate.toString());
//            returnIntent.putExtra("endDate",endDate.toString());
//            returnIntent.putExtra("courseStatus",courseStatus);
            currentCourse.setCourseName(String.valueOf(courseNameEditText.getText()));
            currentCourse.setCourseStartDate(startDate);
            currentCourse.setCourseEndDate(endDate);
            currentCourse.setCourseStatus(courseStatus);

            AddTermActivity.currentTerm.addTermCourse(currentCourse);

            setResult(RESULT_OK, returnIntent);
            finish();
        }
        currentCourse = null;
    }

    @Override
    public void onAssessmentClick(View view, Assessment assessment) {
        //TODO: load assessment details activity

    }

    @Override
    public void onInstructorClick(View view, Instructor instructor) {
        //TODO: load instructor details activity
    }


    @Override
    public void onNoteClick(View view, Note note) {
        //TODO: load note details activity
    }
}