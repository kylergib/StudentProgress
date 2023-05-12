package com.kgibs87.studentprogress.holder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.model.Note;

public class  NoteHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Note note;
        private TextView mTextView;
        private OnNoteClickListener listener;

        public interface OnNoteClickListener {
            void onNoteClick(View view, Note note);
        }

        public NoteHolder(LayoutInflater inflater, ViewGroup parent, OnNoteClickListener listener) {
            super(inflater.inflate(R.layout.recycler_view_terms, parent, false));
            this.listener = listener;
            itemView.setOnClickListener(this);
            mTextView = itemView.findViewById(R.id.termView);
        }

        public void bind(Note note, int position) {
            this.note = note;
            mTextView.setText(note.getMessage());

            // Make the background color dependent on the length of the subject string
//            int colorIndex = subject.getText().length() % mSubjectColors.length;
//            mTextView.setBackgroundColor(mSubjectColors[colorIndex]);
        }

        @Override
        public void onClick(View view) {
            listener.onNoteClick(view, note);

        }
    }
