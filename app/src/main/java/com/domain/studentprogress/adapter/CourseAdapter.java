package com.domain.studentprogress.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.model.Course;
import com.domain.studentprogress.holder.CourseHolder;

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