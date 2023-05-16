package com.domain.studentprogress.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.domain.studentprogress.adapter.TermAdapter;
import com.domain.studentprogress.controller.add.AddTermActivity;
import com.domain.studentprogress.controller.detail.TermDetailsActivity;
import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.holder.TermHolder;
import com.domain.studentprogress.model.Assessment;
import com.domain.studentprogress.model.Course;
import com.domain.studentprogress.model.Instructor;
import com.domain.studentprogress.model.Note;
import com.domain.studentprogress.model.StudentDatabase;
import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.model.Term;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class DashboardActivity extends AppCompatActivity
        implements FloatingButtonFragment.OnButtonClickListener, TermHolder.OnTermClickListener {

    private static StudentDatabase mStudentDb ;

    public static SharedPreferences sharedPref;
    private static boolean notFirstRun;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
        if (!notFirstRun) {
            createFakeData();
            notFirstRun = true;
        }
        sharedPref = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_dashboard);


        TextView welcomeText = findViewById(R.id.welcomeText);

        String welcomeMessage = getResources().getString(R.string.welcome_text);
        welcomeText.setText(String.format(welcomeMessage, sharedPref.getString("name",null)));

        List<Term> termsList = mStudentDb.getTerms();
        for (Term term: termsList) {
            Log.d("DashboardActivity", String.valueOf(term.getId()));
            List<Course> courses = mStudentDb.getCoursesForTerm(term.getId());
            Log.d("DashboardActivity", String.valueOf(courses));
            term.setTermCourses(courses);
            courses.forEach(course -> {
//                List<Instructor> courseInstructors = mStudentDb.getInstructorsForTerm(course.getId());
                course.setCourseInstructors(mStudentDb.getInstructorsForCourse(course.getId()));
                Log.d("DashboardActivity-instructors", String.valueOf(course.getCourseInstructors()));

                course.setCourseAssessments(mStudentDb.getAssessmentsForCourse(course.getId()));
                Log.d("DashboardActivity-assessments", String.valueOf(course.getCourseAssessments()));
                course.setCourseNotes(mStudentDb.getNotesForCourse(course.getId()));
                Log.d("DashboardActivity-assessments", String.valueOf(course.getCourseNotes()));
            });

        }

        RecyclerView termRecyclerView = findViewById(R.id.termRecyclerView);

        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        termRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration();
        termRecyclerView.addItemDecoration(dividerItemDecoration);
        termRecyclerView.setAdapter(new TermAdapter(termsList, this));
        TextView statusText = findViewById(R.id.statusText);



        if (Objects.requireNonNull(termRecyclerView.getAdapter()).getItemCount() > 0) statusText.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment addButtonFragment = fragmentManager.findFragmentById(R.id.addTermButtonFragmentContainer);
        if (addButtonFragment == null) {
            String tag = "addTermButton";
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.END | Gravity.BOTTOM;
            addButtonFragment = new FloatingButtonFragment(tag,params, R.drawable.plus_sign);

            fragmentManager.beginTransaction()
                    .add(R.id.addTermButtonFragmentContainer, addButtonFragment,tag)
                    .commit();
        }
    }
    @Override
    public void onButtonClick(View view, String tag) {
        Log.d("Add tag", tag);
        Intent termIntent = new Intent(this, TermDetailsActivity.class);
        startActivity(termIntent);
    }

    @Override
    public void onTermClick(View view, Term term) {
        Intent intent = new Intent(getApplicationContext(), TermDetailsActivity.class);
        intent.putExtra("currentTerm", term);
        startActivity(intent);
    }


    //TODO: possibly change how below border looks for recyclerview
    private static class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private final Paint mPaint;

        public DividerItemDecoration() {
            mPaint = new Paint();
            mPaint.setColor(Color.RED);
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = 5; // set the height of the border
        }

        @Override
        public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDraw(canvas, parent, state);

            // set the width and height of the border
            int borderHeight = 5;

            // create a new ShapeDrawable with red color
            ShapeDrawable border = new ShapeDrawable();
            border.setShape(new RectShape());
            border.getPaint().setColor(mPaint.getColor());
            border.getPaint().setStyle(Paint.Style.FILL);

            // draw the border on the bottom of each item
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                border.setBounds(
                        child.getLeft(),
                        child.getBottom() - borderHeight,
                        child.getRight(),
                        child.getBottom());
                border.draw(canvas);
            }
        }
    }

    //TODO: remove in future
    public void createFakeData() {
        Term newTerm = new Term("Term 1", LocalDate.of(2023,1,1),
                LocalDate.of(2023,6,30));
        Course newCourse = new Course("Test Course",LocalDate.of(2023,1,1),
                LocalDate.of(2023,1,2),"plan to take");
        Assessment newAssessment = new Assessment("Assessment Test", LocalDate.of(2023,1,1),
                LocalDate.of(2023,1,1),"performance");
        Note newNote = new Note("this is a message");
        Instructor newInstructor = new Instructor("Instructor name",
                "7652151070","testemail@gmail.com");
        newCourse.addCourseAssessment(newAssessment);
        newCourse.addCourseNote(newNote);
        newCourse.addCourseInstructor(newInstructor);

        newTerm.addTermCourse(newCourse);
        Log.d("AddTermTest course", String.valueOf(newTerm.getTermCourses()));
        for (Course course: newTerm.getTermCourses()) {
            Log.d("AddTermTest course", course.getCourseName());
        }

        //add term to database
        long termId = mStudentDb.addTerm(newTerm);
        Log.d("AddTermTest", String.valueOf(termId));
        newTerm.setId(termId);

        boolean courseListNotEmpty = !newTerm.getTermCourses().isEmpty();
        Log.d("AddTermTest course", String.valueOf(courseListNotEmpty));


        if (courseListNotEmpty) {
            newTerm.getTermCourses().forEach(course -> {
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

    }


}