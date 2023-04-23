package com.kgibs87.studentprogress.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgibs87.studentprogress.R;


public class FloatingButtonFragment extends Fragment implements View.OnClickListener {

    private OnButtonClickListener listener;
    private String fragmentTag;
    private FrameLayout.LayoutParams layoutParams;
    private int buttonSource;


    public interface OnButtonClickListener {
        void onButtonClick(View view, String tag);
    }
    @Override
    public void onClick(View view) {
        listener.onButtonClick(view, fragmentTag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Make sure the parent activity implements the interface
        if (context instanceof OnButtonClickListener) {
            listener = (OnButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    public FloatingButtonFragment(String fragmentTag, FrameLayout.LayoutParams layoutParams,
                                  int buttonSource) {
        this.fragmentTag = fragmentTag;
        this.layoutParams = layoutParams;
        this.buttonSource = buttonSource;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_floating_button, container, false);

        FrameLayout buttonFrame = rootView.findViewById(R.id.buttonFrame);

        buttonFrame.setLayoutParams(layoutParams);

        FloatingActionButton floatButton = rootView.findViewById(R.id.floatingButton);
        floatButton.setOnClickListener(this);
        floatButton.setImageResource(buttonSource);
        floatButton.setTag(fragmentTag);
        return rootView;
    }
}