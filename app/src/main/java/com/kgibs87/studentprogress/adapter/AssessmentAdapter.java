package com.kgibs87.studentprogress.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.kgibs87.studentprogress.holder.AssessmentHolder;
import com.kgibs87.studentprogress.model.Assessment;

import java.util.List;

public class AssessmentAdapter  extends RecyclerView.Adapter<AssessmentHolder> {

        private List<Assessment> assessmentList;
        private AssessmentHolder.OnAssessmentClickListener listener;
        public AssessmentAdapter(List<Assessment> assessments,AssessmentHolder.OnAssessmentClickListener listener) {
            assessmentList = assessments;
            this.listener = listener;
        }

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
