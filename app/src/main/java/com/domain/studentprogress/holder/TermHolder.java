package com.domain.studentprogress.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.model.Term;

public  class TermHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

    private Term term;
    private TextView mTextView;

    private OnTermClickListener listener;

    public interface OnTermClickListener {
        void onTermClick(View view, Term term);
    }

    public TermHolder(LayoutInflater inflater, ViewGroup parent, OnTermClickListener listener) {
        super(inflater.inflate(R.layout.recycler_view_terms, parent, false));
        this.listener = listener;
        itemView.setOnClickListener(this);
        mTextView = itemView.findViewById(R.id.termView);
    }

    public void bind(Term term, int position) {
        this.term = term;
        mTextView.setText(term.getName());

    }

    @Override
    public void onClick(View view) {
        listener.onTermClick(view, term);
    }
}
