package com.domain.studentprogress.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.domain.studentprogress.adapter.TermAdapter;
import com.domain.studentprogress.controller.detail.TermDetailsActivity;
import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.holder.TermHolder;
import com.domain.studentprogress.model.Course;
import com.domain.studentprogress.model.StudentDatabase;
import com.domain.studentprogress.util.MyReceiver;
import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.model.Term;

import java.util.List;
import java.util.Objects;

public class DashboardActivity extends AppCompatActivity
        implements FloatingButtonFragment.OnButtonClickListener, TermHolder.OnTermClickListener {

    private static StudentDatabase mStudentDb ;

    public static SharedPreferences sharedPref;
    public static final int TERM_REQUEST_CODE = 1111;
    public static Context appContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
        appContext = getApplicationContext();
        sharedPref = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_dashboard);


        TextView welcomeText = findViewById(R.id.welcomeText);

        String welcomeMessage = getResources().getString(R.string.welcome_text);
        welcomeText.setText(String.format(welcomeMessage, sharedPref.getString("name",null)));

        setTermRecyclerView();

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
    public static void sendNotification(Context context,String message,long trigger) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(appContext.ALARM_SERVICE);
        Intent intent = new Intent(context, MyReceiver.class);
        intent.putExtra("message",message);
        PendingIntent senderStart = PendingIntent.getBroadcast(context,MainActivity.numAlert,intent,PendingIntent.FLAG_IMMUTABLE);

        alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,senderStart);
//        MainActivity.numAlert += 1;
        MainActivity.addNumNotification();

    }
    public void setTermRecyclerView() {
        List<Term> termsList = mStudentDb.getTerms();
        for (Term term: termsList) {
            Log.d("DashboardActivity", String.valueOf(term.getId()));
            List<Course> courses = mStudentDb.getCoursesForTerm(term.getId());
            Log.d("DashboardActivity", String.valueOf(courses));
            term.setTermCourses(courses);
            courses.forEach(course -> {
                course.setCourseInstructors(mStudentDb.getInstructorsForCourse(course.getId()));
                Log.d("DashboardActivity-instructors", String.valueOf(course.getCourseInstructors()));

                course.setCourseAssessments(mStudentDb.getAssessmentsForCourse(course.getId()));
                Log.d("DashboardActivity-assessments", String.valueOf(course.getCourseAssessments()));
                course.setCourseNotes(mStudentDb.getNotesForCourse(course.getId()));
                Log.d("DashboardActivity-assessments", String.valueOf(course.getCourseNotes()));
            });

        }
        RecyclerView termRecyclerView = findViewById(R.id.termRecyclerView);
        int colSize;
        if (termsList.size() > 1) colSize = 2;
        else colSize = 1;

        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(this,colSize);

        termRecyclerView.setLayoutManager(layoutManager);
        termRecyclerView.setAdapter(new TermAdapter(termsList, this));
        TextView statusText = findViewById(R.id.statusText);

        if (Objects.requireNonNull(termRecyclerView.getAdapter()).getItemCount() > 0) statusText.setVisibility(View.GONE);
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
        startActivityForResult(intent,TERM_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TERM_REQUEST_CODE) {
            setTermRecyclerView();
        }
    }



}