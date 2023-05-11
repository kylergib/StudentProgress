package com.kgibs87.studentprogress.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.kgibs87.studentprogress.holder.CourseHolder;
import com.kgibs87.studentprogress.model.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {

    private List<Course> courseList;
    private CourseHolder.OnCourseClickListener listener;

    public CourseAdapter(List<Course> courses, CourseHolder.OnCourseClickListener listener) {
        courseList = courses;
        this.listener = listener;
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CourseHolder(layoutInflater, parent, listener);
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