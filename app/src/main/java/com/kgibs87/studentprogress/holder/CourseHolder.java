package com.kgibs87.studentprogress.holder;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.controller.detail.CourseDetailsActivity;
import com.kgibs87.studentprogress.controller.detail.TermDetailsActivity;
import com.kgibs87.studentprogress.model.Course;

public class CourseHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private Course course;
    private TextView mTextView;
    private OnCourseClickListener listener;


    public interface OnCourseClickListener {
        void onCourseClick(View view, Course course);
    }

    public CourseHolder(LayoutInflater inflater, ViewGroup parent, OnCourseClickListener listener) {
        super(inflater.inflate(R.layout.recycler_view_terms, parent, false));
        this.listener = listener;
        itemView.setOnClickListener(this);
        mTextView = itemView.findViewById(R.id.termView);


    }

    public void bind(Course course, int position) {
        this.course = course;
        mTextView.setText(course.getCourseName());


    }

    @Override
    public void onClick(View view) {
        listener.onCourseClick(view, course);

    }

}
