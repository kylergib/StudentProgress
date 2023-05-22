package com.domain.studentprogress.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.model.Note;
import com.domain.studentprogress.util.FormatStrings;
import com.kgibs87.studentprogress.R;

public class  NoteHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Note note;
        private TextView messageTextView;
        private OnNoteClickListener listener;

        public interface OnNoteClickListener {
            void onNoteClick(View view, Note note);
        }

        public NoteHolder(LayoutInflater inflater, ViewGroup parent, OnNoteClickListener listener) {
            super(inflater.inflate(R.layout.recycler_view_note, parent, false));
            this.listener = listener;
            itemView.setOnClickListener(this);
            messageTextView = itemView.findViewById(R.id.noteMessageTextView);
        }

        public void bind(Note note, int position) {
            this.note = note;
            messageTextView.setText(FormatStrings.formatBetween(note.getMessage(), 30));

            // Make the background color dependent on the length of the subject string
//            int colorIndex = subject.getText().length() % mSubjectColors.length;
//            mTextView.setBackgroundColor(mSubjectColors[colorIndex]);
        }

        @Override
        public void onClick(View view) {
            listener.onNoteClick(view, note);

        }
    }
