package com.kgibs87.studentprogress.holder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.model.Instructor;

public class  InstructorHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Instructor instructor;
        private TextView mTextView;
        private OnInstrcutorClickListener listener;

    public interface OnInstrcutorClickListener {
        void onInstructorClick(View view, Instructor instructor);
    }

        public InstructorHolder(LayoutInflater inflater, ViewGroup parent,
                                OnInstrcutorClickListener listener) {
            super(inflater.inflate(R.layout.recycler_view_terms, parent, false));
            this.listener = listener;
            itemView.setOnClickListener(this);
            mTextView = itemView.findViewById(R.id.termView);
        }

        public void bind(Instructor instructor, int position) {
            this.instructor = instructor;
            mTextView.setText(instructor.getInstructorName());

            // Make the background color dependent on the length of the subject string
//            int colorIndex = subject.getText().length() % mSubjectColors.length;
//            mTextView.setBackgroundColor(mSubjectColors[colorIndex]);
        }

        @Override
        public void onClick(View view) {
            listener.onInstructorClick(view, instructor);

        }
    }
