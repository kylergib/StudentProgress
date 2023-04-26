package com.kgibs87.studentprogress.controller;

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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.fragment.DateFragment;
import com.kgibs87.studentprogress.fragment.FloatingButtonFragment;
import com.kgibs87.studentprogress.model.Course;
import com.kgibs87.studentprogress.model.StudentDatabase;
import com.kgibs87.studentprogress.model.Term;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddTermActivity extends AppCompatActivity implements DateFragment.OnDateSelectedListener, FloatingButtonFragment.OnButtonClickListener {

    private static StudentDatabase mStudentDb ;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now();
    public static final int COURSE_REQUEST_CODE = 1234;
    private List<Course> courseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
        setContentView(R.layout.activity_add_term);

        courseList = new ArrayList<>();
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
            String courseName = data.getStringExtra("courseName");
            LocalDate startDate = LocalDate.parse(data.getStringExtra("startDate"));
            LocalDate endDate = LocalDate.parse(data.getStringExtra("endDate"));
            String courseStatus = data.getStringExtra("courseStatus");
            Log.d("COURSEBACK",String.format("%s - %s - %s - %s",courseName, startDate, endDate, courseStatus));
            Course newCourse = new Course(courseName,startDate,endDate,courseStatus);
            courseList.add(newCourse);
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
//            long termId = mStudentDb.addTerm(newTerm);
//            Log.d("AddTermTest", String.valueOf(termId));
//            newTerm.setId(termId);
            //TODO: once i add all courses/instructors/notes/assessments insert all into database.

            Intent dashboardIntent = new Intent(this, DashboardActivity.class);
            startActivity(dashboardIntent);

        }
    }
    public void updateCourses() {
        RecyclerView courseRecyclerView = findViewById(R.id.courseRecyclerView);

        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        courseRecyclerView.setLayoutManager(linearLayoutManager);
        courseRecyclerView.setAdapter(new CourseAdapter(courseList));

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

            // Make the background color dependent on the length of the subject string
//            int colorIndex = subject.getText().length() % mSubjectColors.length;
//            mTextView.setBackgroundColor(mSubjectColors[colorIndex]);
        }

        @Override
        public void onClick(View view) {
            Log.i("INFO_TAG", "Clicking Test 1");

            Intent termIntent = new Intent(getApplicationContext(), TermDetailsActivity.class);
            startActivity(termIntent);

            //TODO: finish what clicking  does

        }
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
