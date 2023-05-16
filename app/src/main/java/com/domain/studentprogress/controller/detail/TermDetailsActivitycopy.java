package com.domain.studentprogress.controller.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.adapter.CourseAdapter;
import com.domain.studentprogress.controller.DashboardActivity;
import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.holder.CourseHolder;
import com.domain.studentprogress.model.Course;
import com.domain.studentprogress.model.StudentDatabase;
import com.domain.studentprogress.model.Term;
import com.kgibs87.studentprogress.R;

public class TermDetailsActivitycopy extends AppCompatActivity implements
        CourseHolder.OnCourseClickListener,
        FloatingButtonFragment.OnButtonClickListener {

    public Term currentTerm;
    private StudentDatabase mStudentDb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
        setContentView(R.layout.activity_detail_term);

        Button deleteTermButton = findViewById(R.id.deleteTermButton);
        deleteTermButton.setBackgroundColor(Color.rgb(168,0,0));


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment addButtonFragment = fragmentManager.findFragmentById(R.id.addButtonFragmentContainer);
        if (addButtonFragment == null) {
            //TODO: change to edit button?
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
            //todo: change logo to back and not an x
            backButtonFragment = new FloatingButtonFragment(cancelTag,params,R.drawable.baseline_close);
            fragmentManager.beginTransaction()
                    .add(R.id.addButtonFragmentContainer, backButtonFragment,cancelTag)
                    .commit();
        }



        Intent intent = getIntent();

        boolean intentHasTerm = intent.hasExtra("currentTerm");

        if (intentHasTerm) {
            currentTerm = (Term) intent.getSerializableExtra("currentTerm");
            setHeader();
            setRecyclerCourse();
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

    @Override
    public void onCourseClick(View view, Course course) {
        Log.d("onCourseClick", "onCourseClick: " + course.getCourseName());
        Intent intent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
        intent.putExtra("currentCourse", course);
        startActivity(intent);
    }

    @Override
    public void onButtonClick(View view, String tag) {
        Log.d("AddTermActivity", tag);
        if (tag.equals("cancelTermButton")) {
            currentTerm = null;
            finish();
        } else if (tag.equals("saveTermButton")) {
            //TODO: finish
//            saveTerm();
        }
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
}