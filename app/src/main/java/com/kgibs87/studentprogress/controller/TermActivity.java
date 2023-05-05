package com.kgibs87.studentprogress.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.fragment.DateFragment;
import com.kgibs87.studentprogress.fragment.FloatingButtonFragment;
import com.kgibs87.studentprogress.model.Course;
import com.kgibs87.studentprogress.model.StudentDatabase;
import com.kgibs87.studentprogress.model.Term;

import java.time.LocalDate;
import java.util.List;

public class TermActivity extends AppCompatActivity implements DateFragment.OnDateSelectedListener, FloatingButtonFragment.OnButtonClickListener {

    private static StudentDatabase mStudentDb ;
    private LocalDate startDate = LocalDate.now();
    public LocalDate endDate = LocalDate.now();
    public static final int COURSE_REQUEST_CODE = 1234;
    public static Term currentTerm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
        setContentView(R.layout.activity_term);

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
        Log.d("Debug-teg", "addcourseclicked");
        Intent courseIntent = new Intent(getApplicationContext(), AddCourseActivity.class);
//        startActivity(courseIntent);
        startActivityForResult(courseIntent,COURSE_REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COURSE_REQUEST_CODE && resultCode == RESULT_OK) {
//            String courseName = data.getStringExtra("courseName");
//            LocalDate startDate = LocalDate.parse(data.getStringExtra("startDate"));
//            LocalDate endDate = LocalDate.parse(data.getStringExtra("endDate"));
//            String courseStatus = data.getStringExtra("courseStatus");
//            Log.d("COURSEBACK",String.format("%s - %s - %s - %s",courseName, startDate, endDate, courseStatus));
//            Course newCourse = new Course(courseName,startDate,endDate,courseStatus);
//            currentTerm.addTermCourse(newCourse);
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
            finish();
        } else if (tag.equals("saveTermButton")) {
            saveTerm();
        }
        currentTerm = null;
    }
    public void updateCourses() {
        RecyclerView courseRecyclerView = findViewById(R.id.courseRecyclerView);

        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        courseRecyclerView.setLayoutManager(linearLayoutManager);
        courseRecyclerView.setAdapter(new CourseAdapter(currentTerm.getTermCourses()));
    }

    public void addTerm() {

    }




    private class CourseHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Course course;
        private TextView mTextView;

        public CourseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recycler_view_terms, parent, false));
            itemView.setOnClickListener(this);
            mTextView = itemView.findViewById(R.id.termView);
        }

        public void bind(Course course, int position) {
            this.course = course;
            mTextView.setText(course.getCourseName());

        }

        @Override
        public void onClick(View view) {
            Log.i("INFO_TAG", "Clicking Test 1");

            Intent termIntent = new Intent(getApplicationContext(), TermDetailsActivity.class);
            startActivity(termIntent);

            //TODO: finish what clicking  does

        }
    }

    public void saveTerm() {
        EditText termNameEditText = findViewById(R.id.termNameEditText);
        String termNameString = termNameEditText.getText().toString();

        boolean endBeforeStart = endDate.isBefore(startDate);
        boolean startEqualsEnd = startDate.isEqual(endDate);
        boolean termNameEmpty = (termNameString.isEmpty());

        if (endBeforeStart) {
            Log.d("AddTermActivity", "Start date is not before the end date.");
            Toast.makeText(TermActivity.this, "Start date is not before the end date.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else if (startEqualsEnd) {
            Log.d("AddTermActivity", "Start date and end date cannot be the same");
            Toast.makeText(TermActivity.this, "Start date and end date cannot be the same",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (termNameEmpty) {
            Log.d("AddTermActivity", "Term name cannot be empty");
            Toast.makeText(TermActivity.this, "Term name cannot be empty",
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

        //TODO: leftoff



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

    }


    private class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {

        private List<Course> courseList;

        public CourseAdapter(List<Course> courses) {
            courseList = courses;
        }

        @Override
        public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new CourseHolder(layoutInflater, parent);
        }


        @Override
        public void onBindViewHolder(CourseHolder holder, int position){
            holder.bind(courseList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return courseList.size();
        }
    }

}