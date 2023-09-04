package com.domain.studentprogress.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.domain.studentprogress.holder.TermHolder;
import com.domain.studentprogress.model.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermHolder> {

    private final List<Term> termList;
    private final TermHolder.OnTermClickListener listener;

    public TermAdapter(List<Term> terms, TermHolder.OnTermClickListener listener) {
        termList = terms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new TermHolder(layoutInflater, parent, listener);
    }

    @Override
    public void onBindViewHolder(TermHolder holder, int position){
        holder.bind(termList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

}
