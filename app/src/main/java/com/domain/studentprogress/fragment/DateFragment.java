package com.domain.studentprogress.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.kgibs87.studentprogress.R;

import java.time.LocalDate;

public class DateFragment extends Fragment implements DatePicker.OnDateChangedListener {

    private OnDateSelectedListener listener;
    private final String fragmentTag;
    private int year = 0;
    private int month = 0;
    private int day = 0;

    // Interface to communicate with parent activity
    public interface OnDateSelectedListener {
        void onDateSelected(LocalDate localDate, String tag);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Make sure the parent activity implements the interface
        if (context instanceof OnDateSelectedListener) {
            listener = (OnDateSelectedListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement OnDateSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    // Inside the DatePicker's OnDateChangedListener
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        LocalDate selectedDate = LocalDate.of(year,monthOfYear+1,dayOfMonth);
        listener.onDateSelected(selectedDate, fragmentTag);
    }


    public DateFragment(String fragmentTag) {
        // Required empty public constructor
        this.fragmentTag = fragmentTag;
        }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_date, container, false);

        // Find the DatePicker view in the layout
        DatePicker datePicker = rootView.findViewById(R.id.datePicker);

        // Set the OnDateChangedListener on the DatePicker view
        datePicker.setOnDateChangedListener(this);
        datePicker.setTag(fragmentTag);
        if (year != 0 && month != 0 && day != 0) datePicker.updateDate(year,month-1,day);

        return rootView;
    }

    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;

    }
}