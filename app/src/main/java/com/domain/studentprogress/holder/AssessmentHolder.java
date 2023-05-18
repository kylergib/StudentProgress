package com.domain.studentprogress.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.model.Assessment;

public class AssessmentHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

    private Assessment assessment;
    private TextView mTextView;
    private OnAssessmentClickListener listener;

    public interface OnAssessmentClickListener {
        void onAssessmentClick(View view, Assessment assessment);
    }

    public AssessmentHolder(LayoutInflater inflater, ViewGroup parent, OnAssessmentClickListener listener) {
        super(inflater.inflate(R.layout.recycler_view_terms, parent, false));
        itemView.setOnClickListener(this);
        this.listener = listener;
        mTextView = itemView.findViewById(R.id.termNameTextView);
    }

    public void bind(Assessment assessment, int position) {
        this.assessment = assessment;
        mTextView.setText(assessment.getAssessmentTitle());
    }

    @Override
    public void onClick(View view) {
       listener.onAssessmentClick(view, assessment);

    }
}