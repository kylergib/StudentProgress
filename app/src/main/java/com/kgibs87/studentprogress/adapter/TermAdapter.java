package com.kgibs87.studentprogress.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.kgibs87.studentprogress.holder.TermHolder;
import com.kgibs87.studentprogress.model.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermHolder> {

    private List<Term> termList;
    private TermHolder.OnTermClickListener listener;

    public TermAdapter(List<Term> terms, TermHolder.OnTermClickListener listener) {
        termList = terms;
        this.listener = listener;
    }

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

    public List<Term> getTermList() {
        return termList;
    }
}
