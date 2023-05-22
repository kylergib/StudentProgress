package com.domain.studentprogress.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.model.Instructor;
import com.domain.studentprogress.util.FormatPhoneNumber;
import com.kgibs87.studentprogress.R;

public class  InstructorHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Instructor instructor;
        private TextView instructorNameTextView;
        private TextView instructorEmailTextView;
        private TextView instructorPhoneNumberTextView;
        private OnInstrcutorClickListener listener;

    public interface OnInstrcutorClickListener {
        void onInstructorClick(View view, Instructor instructor);
    }

        public InstructorHolder(LayoutInflater inflater, ViewGroup parent,
                                OnInstrcutorClickListener listener) {
            super(inflater.inflate(R.layout.recycler_view_instructor, parent, false));
            this.listener = listener;
            itemView.setOnClickListener(this);
            instructorNameTextView = itemView.findViewById(R.id.instructorNameTextView);
            instructorEmailTextView = itemView.findViewById(R.id.instructorEmailTextView);
            instructorPhoneNumberTextView = itemView.findViewById(R.id.instructorPhoneNumberTextView);
        }

        public void bind(Instructor instructor, int position) {
            this.instructor = instructor;
            instructorNameTextView.setText(instructor.getInstructorName());
            instructorEmailTextView.setText(instructor.getInstructorEmail());
            String phoneNumber = FormatPhoneNumber.formatPhoneNumber(instructor.getInstructorPhoneNumber());
            instructorPhoneNumberTextView.setText(phoneNumber);

        }

        @Override
        public void onClick(View view) {
            listener.onInstructorClick(view, instructor);

        }
    }
