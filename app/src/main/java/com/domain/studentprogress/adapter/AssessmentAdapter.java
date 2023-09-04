package com.domain.studentprogress.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.holder.AssessmentHolder;
import com.domain.studentprogress.model.Assessment;

import java.util.List;

public class AssessmentAdapter  extends RecyclerView.Adapter<AssessmentHolder> {

        private final List<Assessment> assessmentList;
        private final AssessmentHolder.OnAssessmentClickListener listener;
        public AssessmentAdapter(List<Assessment> assessments,AssessmentHolder.OnAssessmentClickListener listener) {
            assessmentList = assessments;
            this.listener = listener;
        }

        @NonNull
        @Override
        public AssessmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new AssessmentHolder(layoutInflater, parent, listener);
        }


        @Override
        public void onBindViewHolder(AssessmentHolder holder, int position){
            holder.bind(assessmentList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return assessmentList.size();
        }
    }
