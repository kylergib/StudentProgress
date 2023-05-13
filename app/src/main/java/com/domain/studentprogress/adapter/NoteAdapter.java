package com.domain.studentprogress.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.holder.NoteHolder;
import com.domain.studentprogress.model.Note;

import java.util.List;

public class  NoteAdapter extends RecyclerView.Adapter<NoteHolder> {

        private List<Note> noteList;
        private NoteHolder.OnNoteClickListener listener;

        public NoteAdapter(List<Note> notes, NoteHolder.OnNoteClickListener listener) {
            noteList = notes;
            this.listener = listener;

        }

        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new NoteHolder(layoutInflater, parent, listener);
        }


        @Override
        public void onBindViewHolder(NoteHolder holder, int position){
            holder.bind(noteList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return noteList.size();
        }
    }
