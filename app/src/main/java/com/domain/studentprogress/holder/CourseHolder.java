package com.domain.studentprogress.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.model.Course;
import com.kgibs87.studentprogress.R;

import java.time.format.DateTimeFormatter;

public class CourseHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private Course course;
    private TextView courseNameTextView;
    private TextView courseDateTextView;
    private TextView courseStatusTextView;
    private OnCourseClickListener listener;


    public interface OnCourseClickListener {
        void onCourseClick(View view, Course course);
    }

    public CourseHolder(LayoutInflater inflater, ViewGroup parent, OnCourseClickListener listener) {
        super(inflater.inflate(R.layout.recycler_view_course, parent, false));
        this.listener = listener;
        itemView.setOnClickListener(this);
        courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
        courseDateTextView = itemView.findViewById(R.id.courseDateTextView);
        courseStatusTextView = itemView.findViewById(R.id.courseStatusTextView);


    }

    public void bind(Course course, int position) {
        this.course = course;
        courseNameTextView.setText(course.getCourseName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, u");
        String dateRange = String.format("%s - %s", course.getStartDate().format(formatter),course.getEndDate().format(formatter));
        courseDateTextView.setText(dateRange);
        courseStatusTextView.setText(String.format("Status: %s", course.getStatus()));


    }

    @Override
    public void onClick(View view) {
        listener.onCourseClick(view, course);

    }

}
