package com.domain.studentprogress.controller.add;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.adapter.CourseAdapter;
import com.domain.studentprogress.fragment.DateFragment;
import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.model.Course;
import com.domain.studentprogress.model.StudentDatabase;
import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.controller.DashboardActivity;
import com.domain.studentprogress.holder.CourseHolder;
import com.domain.studentprogress.model.Term;

import java.time.LocalDate;

public class AddTermActivity extends AppCompatActivity implements DateFragment.OnDateSelectedListener,
        FloatingButtonFragment.OnButtonClickListener, CourseHolder.OnCourseClickListener {

    private static StudentDatabase mStudentDb ;
    private LocalDate startDate = LocalDate.now();
    public LocalDate endDate = LocalDate.now();
    public static final int COURSE_REQUEST_CODE = 1234;
    public static Term currentTerm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
        setContentView(R.layout.activity_add_term);

        Intent intent = getIntent();

        boolean intentHasTerm = intent.hasExtra("currentTerm");

        if (intentHasTerm) {
            currentTerm = (Term) intent.getSerializableExtra("currentTerm");
            TextView headerText = findViewById(R.id.headerTitle);
            headerText.setText(currentTerm.getName());
        } else {
            addTermSetUp();
        }


    }

    public void addTermSetUp() {
        Log.d("AddTermActivity", "starting set up");
        currentTerm = new Term();
        //TODO: get course data from database
        updateCourses();

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
        Log.d("Debug-teg", "addCourseClicked");
        Log.d("AddTermActivity", String.valueOf(currentTerm));
        Intent courseIntent = new Intent(getApplicationContext(), AddCourseActivity.class);

        startActivityForResult(courseIntent,COURSE_REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COURSE_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("AddTermActivity", "returning to activity");
            Log.d("AddTermActivity", String.valueOf(currentTerm));
            updateCourses();
        }
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
            Log.d("AddNoteActivity", tag + " selected: " + localDate.toString());
            endDate = localDate;
        }
    }

    @Override
    public void onButtonClick(View view, String tag) {
        Log.d("AddTermActivity", tag);
        if (tag.equals("cancelTermButton")) {
            currentTerm = null;
            finish();
        } else if (tag.equals("saveTermButton")) {
            saveTerm();
        }

    }
    public void updateCourses() {
        RecyclerView courseRecyclerView = findViewById(R.id.courseRecyclerView);

        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        courseRecyclerView.setLayoutManager(linearLayoutManager);
        courseRecyclerView.setAdapter(new CourseAdapter(currentTerm.getTermCourses(),this));
    }


    public void saveTerm() {

        Log.d("AddTermActivity", "returning to activity");
        Log.d("AddTermActivity", String.valueOf(currentTerm));

        EditText termNameEditText = findViewById(R.id.termNameEditText);
        String termNameString = termNameEditText.getText().toString();

        boolean endBeforeStart = endDate.isBefore(startDate);
        boolean startEqualsEnd = startDate.isEqual(endDate);
        boolean termNameEmpty = (termNameString.isEmpty());

        if (endBeforeStart) {
            Log.d("AddTermActivity", "Start date is not before the end date.");
            Toast.makeText(AddTermActivity.this, "Start date is not before the end date.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else if (startEqualsEnd) {
            Log.d("AddTermActivity", "Start date and end date cannot be the same");
            Toast.makeText(AddTermActivity.this, "Start date and end date cannot be the same",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (termNameEmpty) {
            Log.d("AddTermActivity", "Term name cannot be empty");
            Toast.makeText(AddTermActivity.this, "Term name cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        currentTerm.setName(termNameString);
        currentTerm.setStartDate(startDate);
        currentTerm.setEndDate(endDate);
        Log.d("Add Term Tag", String.format("%s - %s - %s",
                currentTerm.getName(), currentTerm.getStartDate().toString(),
                currentTerm.getEndDate().toString()));

        //add term to database
        long termId = mStudentDb.addTerm(currentTerm);
        Log.d("AddTermTest", String.valueOf(termId));
        currentTerm.setId(termId);

        boolean courseListNotEmpty = !currentTerm.getTermCourses().isEmpty();


        if (courseListNotEmpty) {
            currentTerm.getTermCourses().forEach(course -> {
                boolean assessmentListNotEmpty = !course.getCourseAssessments().isEmpty();
                boolean instructorListNotEmpty = !course.getCourseInstructors().isEmpty();
                boolean noteListNotEmpty = !course.getCourseNotes().isEmpty();
                course.setTermID(termId);
                long courseId = mStudentDb.addCourse(course);

                if (courseId < 1) return;

                Log.d("Add Term:", "Added course with ID: " + courseId);
                course.setId(courseId);

                Log.d("Course:", course.getCourseName());
                if (assessmentListNotEmpty) {
                    course.getCourseAssessments().forEach(assessment -> {
                        assessment.setCourseID(courseId);
                        long assessmentId = mStudentDb.addAssessment(assessment);
                        Log.d("Add Term:", "Added assessment with ID: " + assessmentId);
                        assessment.setAssessmentId(assessmentId);

                    });
                } else {
                    Log.d("Add term", "Assessment List is empty");
                }
                if (instructorListNotEmpty) {
                    course.getCourseInstructors().forEach(instructor -> {
                        instructor.setCourseID(courseId);
                        long instructorId = mStudentDb.addInstructor(instructor);
                        Log.d("Add Term:", "Added instructor with ID: " + instructorId);
                        instructor.setId(instructorId);
                    });
                } else {
                    Log.d("Add term", "Instructor List is empty");
                }
                if (noteListNotEmpty) {
                    course.getCourseNotes().forEach(note -> {
                        note.setCourseID(courseId);
                        long noteID = mStudentDb.addNote(note);
                        Log.d("Add Term:", "Added note with ID: " + noteID);
                        note.setId(noteID);
                    });
                } else {
                    Log.d("Add term", "Note List is empty");
                }
            });
        } else {
            Log.d("Add term", "Course List is empty");
        }


        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        startActivity(dashboardIntent);
        currentTerm = null;

    }

    @Override
    public void onCourseClick(View view, Course course) {
        //TODO: add view for course later
    }


}
