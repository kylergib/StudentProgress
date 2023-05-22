package com.domain.studentprogress.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.util.FormatStrings;
import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.model.Assessment;

import java.time.format.DateTimeFormatter;

public class AssessmentHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

    private Assessment assessment;
    private TextView assessmentNameTextView;
    private TextView assessmentTypeTextView;
    private TextView assessmentEndDate;

    private OnAssessmentClickListener listener;

    public interface OnAssessmentClickListener {
        void onAssessmentClick(View view, Assessment assessment);
    }

    public AssessmentHolder(LayoutInflater inflater, ViewGroup parent, OnAssessmentClickListener listener) {
        super(inflater.inflate(R.layout.recycler_view_assessment, parent, false));
        itemView.setOnClickListener(this);
        this.listener = listener;
        assessmentNameTextView = itemView.findViewById(R.id.assessmentNameTextView);
        assessmentTypeTextView = itemView.findViewById(R.id.assessmentType);
        assessmentEndDate = itemView.findViewById(R.id.assessmentEndDateTextView);
    }

    public void bind(Assessment assessment, int position) {
        this.assessment = assessment;
        assessmentNameTextView.setText(FormatStrings.formatBetween(assessment.getAssessmentTitle(), 15));
        assessmentTypeTextView.setText(String.format("Type: %s", assessment.getAssessmentType()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, u");
        String dateString = String.format("End date: %s", assessment.getAssessmentEndDate().format(formatter));
        assessmentEndDate.setText(dateString);
    }

    @Override
    public void onClick(View view) {
       listener.onAssessmentClick(view, assessment);

    }
}