package com.domain.studentprogress.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.holder.InstructorHolder;
import com.domain.studentprogress.model.Instructor;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorHolder> {

        private List<Instructor> instructorList;
        private InstructorHolder.OnInstrcutorClickListener listener;

        public InstructorAdapter(List<Instructor> instructors,
                                 InstructorHolder.OnInstrcutorClickListener listener) {
            instructorList = instructors;
            this.listener = listener;
        }

        @Override
        public InstructorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new InstructorHolder(layoutInflater, parent, listener);
        }


        @Override
        public void onBindViewHolder(InstructorHolder holder, int position){
            holder.bind(instructorList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return instructorList.size();
        }
    }
