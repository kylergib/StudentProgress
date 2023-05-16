package com.domain.studentprogress.controller.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.domain.studentprogress.adapter.CourseAdapter;
import com.domain.studentprogress.fragment.DateFragment;
import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.model.Course;
import com.domain.studentprogress.model.StudentDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.controller.DashboardActivity;
import com.domain.studentprogress.holder.CourseHolder;
import com.domain.studentprogress.model.Term;

import java.time.LocalDate;

public class TermDetailsActivity  extends AppCompatActivity implements DateFragment.OnDateSelectedListener,
        FloatingButtonFragment.OnButtonClickListener, CourseHolder.OnCourseClickListener {

    private static StudentDatabase mStudentDb ;
    private LocalDate startDate = LocalDate.now();
    public LocalDate endDate = LocalDate.now();
    public static final int COURSE_REQUEST_CODE = 1234;
    public static Term currentTerm;
    private Fragment backButtonFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
        setContentView(R.layout.activity_detail_term);

        Intent intent = getIntent();

        boolean intentHasTerm = intent.hasExtra("currentTerm");
        int cancelButtonImage;

        if (intentHasTerm) {
            currentTerm = (Term) intent.getSerializableExtra("currentTerm");
            setHeader();
            setRecyclerCourse();
            //TODO: hide views not needed if you are just viewing term
            TextView startDate = findViewById(R.id.startDate);
            startDate.setVisibility(View.GONE);

            TextView endDate = findViewById(R.id.endDate);
            endDate.setVisibility(View.GONE);

            TextView termName = findViewById(R.id.termNameView);
            termName.setVisibility(View.GONE);

            EditText termNameInput = findViewById(R.id.termNameEditText);
            termNameInput.setVisibility(View.GONE);
            cancelButtonImage = R.drawable.arrow_back;



        } else {
            addTermSetUp();
            cancelButtonImage = R.drawable.baseline_close;
        }
        updateCourses();
        FragmentManager fragmentManager = getSupportFragmentManager();


        backButtonFragment = fragmentManager.findFragmentById(R.id.backButtonFragmentContainer);
        if (backButtonFragment == null) {
            String cancelTag = "cancelTermButton";
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.START | Gravity.BOTTOM;
            backButtonFragment = new FloatingButtonFragment(cancelTag,params,cancelButtonImage);
            fragmentManager.beginTransaction()
                    .add(R.id.addButtonFragmentContainer, backButtonFragment,cancelTag)
                    .commit();
        }
        //TODO: below works to change image, but cannot be in onCreate
//        FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelTermButton");
//        testButton.setImageResource(R.drawable.arrow_back);




    }

    public void addTermSetUp() {
        Log.d("AddTermActivity", "starting set up");
        if (currentTerm == null) currentTerm = new Term();

        Button deleteTermButton = findViewById(R.id.deleteTermButton);
        deleteTermButton.setVisibility(View.GONE);


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

    }

    public void setHeader() {
        TextView headerText = findViewById(R.id.headerTitle);
        headerText.setText(currentTerm.getName());
    }
    public void setRecyclerCourse() {

        Log.d("termDetail", String.valueOf(currentTerm.getTermCourses()));
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new CourseAdapter(currentTerm.getTermCourses(),this));
    }

    public void addCourseClick(View view) {
        Log.d("Debug-teg", "addCourseClicked");
        Log.d("AddTermActivity", String.valueOf(currentTerm));
        Intent courseIntent = new Intent(getApplicationContext(), CourseDetailsActivity.class);

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
            Toast.makeText(TermDetailsActivity.this, "Start date is not before the end date.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else if (startEqualsEnd) {
            Log.d("AddTermActivity", "Start date and end date cannot be the same");
            Toast.makeText(TermDetailsActivity.this, "Start date and end date cannot be the same",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (termNameEmpty) {
            Log.d("AddTermActivity", "Term name cannot be empty");
            Toast.makeText(TermDetailsActivity.this, "Term name cannot be empty",
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
        Log.d("onCourseClick", "onCourseClick: " + course.getCourseName());
        Log.d("onCourseClick", String.valueOf(course.getCourseAssessments()));
        Intent intent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
        intent.putExtra("currentCourse", course);
        startActivity(intent);
    }


    public void deleteTermClick(View view) {
        //todo add pop up to confirm deleting term (cannot be reversed)
        Log.d("TermDetails", "Delete: " + currentTerm.getId());
        boolean deletedTermBool = mStudentDb.deleteTerm(currentTerm.getId());
        if (deletedTermBool) {
            Log.d("deleteTermClick", "Successfully deleted " + currentTerm.getId());
            Intent dashboardIntent = new Intent(this, DashboardActivity.class);
            startActivity(dashboardIntent);
            currentTerm = null;
        }
        else Log.d("deleteTermClick", "Could not delete " + currentTerm.getId());


    }

    //TODO: add edit button


}
