package com.domain.studentprogress.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgibs87.studentprogress.R;


public class FloatingButtonFragment extends Fragment implements View.OnClickListener {

    private OnButtonClickListener listener;
    private final String fragmentTag;
    private final FrameLayout.LayoutParams layoutParams;
    private final int buttonSource;
    private FloatingActionButton floatButton;


    public interface OnButtonClickListener {
        void onButtonClick(View view, String tag);
    }
    @Override
    public void onClick(View view) {
        listener.onButtonClick(view, fragmentTag);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Make sure the parent activity implements the interface
        if (context instanceof OnButtonClickListener) {
            listener = (OnButtonClickListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement OnButtonClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public FloatingButtonFragment() {
        // Required empty public constructor
        this.fragmentTag = null;
        this.layoutParams = null;
        this.buttonSource = 0;
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

        if (layoutParams != null) buttonFrame.setLayoutParams(layoutParams);

        floatButton = rootView.findViewById(R.id.floatingButton);
        floatButton.setOnClickListener(this);
        if (buttonSource != 0) floatButton.setImageResource(buttonSource);
        if (fragmentTag != null) floatButton.setTag(fragmentTag);
        return rootView;
    }

    public void setImage(int buttonSource) {
        floatButton.setImageResource(buttonSource);
    }
}