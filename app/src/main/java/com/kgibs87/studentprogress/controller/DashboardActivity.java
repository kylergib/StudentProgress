package com.kgibs87.studentprogress.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
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
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.adapter.TermAdapter;
import com.kgibs87.studentprogress.controller.add.AddTermActivity;
import com.kgibs87.studentprogress.controller.detail.TermDetailsActivity;
import com.kgibs87.studentprogress.fragment.FloatingButtonFragment;
import com.kgibs87.studentprogress.holder.TermHolder;
import com.kgibs87.studentprogress.model.Course;
import com.kgibs87.studentprogress.model.StudentDatabase;
import com.kgibs87.studentprogress.model.Term;

import java.time.LocalDate;
import java.util.List;

public class DashboardActivity extends AppCompatActivity
        implements FloatingButtonFragment.OnButtonClickListener, TermHolder.OnTermClickListener {

    private static StudentDatabase mStudentDb ;
    private List<Term> termsList;
    public static SharedPreferences sharedPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_dashboard);


        TextView welcomeText = findViewById(R.id.welcomeText);

        String welcomeMessage = getResources().getString(R.string.welcome_text);
        welcomeText.setText(String.format(welcomeMessage, sharedPref.getString("name",null)));
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());

        //TESTING
        Term term1 = new Term("Test1", LocalDate.of(2023,1,1),LocalDate.of(2023,6,30),1);
        Term term2 = new Term("Test2", LocalDate.of(2023,1,1),LocalDate.of(2023,6,30),2);

//        termsList = Arrays.asList(term1,term2);
        termsList = mStudentDb.getTerms();
        for (Term term: termsList) {
            List<Course> courses = mStudentDb.getCoursesForTerm(term.getId());
            Log.d("DashboardActivity", String.valueOf(courses));
            term.setTermCourses(courses);
        }

        RecyclerView termRecyclerView = findViewById(R.id.termRecyclerView);

        RecyclerView.LayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        termRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration();
        termRecyclerView.addItemDecoration(dividerItemDecoration);
        termRecyclerView.setAdapter(new TermAdapter(termsList, this));
        TextView statusText = findViewById(R.id.statusText);



        if (termRecyclerView.getAdapter().getItemCount() > 0) statusText.setVisibility(View.GONE);
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

//    public void addTermClick(View view) {
//        Intent termIntent = new Intent(this, AddTermActivity.class);
//        startActivity(termIntent);
//    }

    @Override
    public void onButtonClick(View view, String tag) {
        Log.d("Add tag", tag);
        Intent termIntent = new Intent(this, AddTermActivity.class);
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
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = 5; // set the height of the border
        }

        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
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

}