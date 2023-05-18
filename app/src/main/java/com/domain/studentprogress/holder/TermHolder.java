package com.domain.studentprogress.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.model.Term;

import java.time.format.DateTimeFormatter;

public  class TermHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

    private Term term;
    private TextView termNameTextView;
    private TextView termDateTextView;
    private TextView numberOfCourses;
    private TextView numberOfCoursesCompleted;
    private OnTermClickListener listener;

    public interface OnTermClickListener {
        void onTermClick(View view, Term term);
    }

    public TermHolder(LayoutInflater inflater, ViewGroup parent, OnTermClickListener listener) {
        super(inflater.inflate(R.layout.recycler_view_terms, parent, false));
        this.listener = listener;
        itemView.setOnClickListener(this);
        termNameTextView = itemView.findViewById(R.id.termNameTextView);
        termDateTextView = itemView.findViewById(R.id.termDateTextView);
        numberOfCourses = itemView.findViewById(R.id.numberOfCourses);
        numberOfCoursesCompleted = itemView.findViewById(R.id.numberOfCoursesCompleted);
    }

    public void bind(Term term, int position) {
        this.term = term;
        termNameTextView.setText(term.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, u");
        String dateRange = String.format("%s - %s", term.getStartDate().format(formatter),term.getEndDate().format(formatter));
        termDateTextView.setText(dateRange);
        numberOfCourses.setText(String.format("No. Courses: %d", term.getTermCourses().size()));
        int coursesCompleted = (int) term.getTermCourses().stream().filter(course -> course.getCourseStatus().equals("completed")).count();
        numberOfCoursesCompleted.setText(String.format("No. completed: %d", coursesCompleted));

    }

    @Override
    public void onClick(View view) {
        listener.onTermClick(view, term);
    }
}
