package com.kgibs87.studentprogress.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.kgibs87.studentprogress.R;

import java.util.Date;

public class AddNoteActivity extends AppCompatActivity implements DateFragment.OnDateSelectedListener{
    private FragmentManager fragmentManager;
    private Date startDate;
    private Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        fragmentManager = getSupportFragmentManager();


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

    }
    public void testClick(View view) {

    }

    @Override
    public void onDateSelected(Date date, String tag) {
        // Check the tag to determine which DateFragment is invoking the method
        if (tag.equals("startDate")) {
            startDate = date;
            Log.d("AddNoteActivity", "Start date selected: " + date.toString());
        } else if (tag.equals("endDate")) {
            endDate = date;
            Log.d("AddNoteActivity", "End date selected: " + date.toString());
        }
    }
}