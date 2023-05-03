package com.kgibs87.studentprogress.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.model.Course;
import com.kgibs87.studentprogress.model.Term;

import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {

    public Term currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        Intent intent = getIntent();

        boolean intentHasTerm = intent.hasExtra("currentTerm");

        if (intentHasTerm) {
            currentTerm = (Term) intent.getSerializableExtra("currentTerm");
            TextView headerText = findViewById(R.id.header_title);
            headerText.setText(currentTerm.getName());
        }
    }

    public void backButtonClick(View view) {
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);

        startActivity(dashboardIntent);
    }

}