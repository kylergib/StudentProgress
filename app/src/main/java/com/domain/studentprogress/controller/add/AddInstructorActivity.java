package com.domain.studentprogress.controller.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.model.Instructor;
import com.kgibs87.studentprogress.R;

public class AddInstructorActivity extends AppCompatActivity implements FloatingButtonFragment.OnButtonClickListener {
    private EditText instructorNameEditText;
    private EditText instructorNumberEditText;
    private EditText instructorEmailEditText;
    private Instructor currentInstructor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);

        instructorNameEditText = findViewById(R.id.instructorNameEditText);
        instructorNumberEditText = findViewById(R.id.instructorNumberEditText);
        instructorEmailEditText = findViewById(R.id.instructorEmailEditText);

        if (currentInstructor == null)
            currentInstructor = new Instructor();


        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment addButtonFragment = fragmentManager.findFragmentById(R.id.addButtonFragmentContainer);
        if (addButtonFragment == null) {
            String saveTag = "saveInstructorButton";
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.END | Gravity.BOTTOM;
            addButtonFragment = new FloatingButtonFragment(saveTag,params, R.drawable.baseline_check);

            fragmentManager.beginTransaction()
                    .add(R.id.addButtonFragmentContainer, addButtonFragment,saveTag)
                    .commit();
        }

        Fragment backButtonFragment = fragmentManager.findFragmentById(R.id.backButtonFragmentContainer);
        if (backButtonFragment == null) {
            String cancelTag = "cancelInstructorButton";
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.START | Gravity.BOTTOM;
            backButtonFragment = new FloatingButtonFragment(cancelTag,params,R.drawable.baseline_close);
            fragmentManager.beginTransaction()
                    .add(R.id.addButtonFragmentContainer, backButtonFragment,cancelTag)
                    .commit();
        }



    }

    @Override
    public void onButtonClick(View view, String tag) {
        if (tag.equals("cancelInstructorButton")) {
            Log.d("Back tag", tag);
            finish();
        } else if (tag.equals("saveInstructorButton")) {
            Log.d("Add tag", tag);
            //TODO: create term object and add to sqlite
            boolean nameEmpty = instructorNameEditText.getText().toString().isEmpty();

            if (nameEmpty) {
                Toast.makeText(AddInstructorActivity.this, "Name cannot be empty",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String name = instructorNameEditText.getText().toString();
            String number = instructorNumberEditText.getText().toString();
            String email = instructorEmailEditText.getText().toString();

            Instructor newInstructor = new Instructor(name, number, email);
            Intent returnIntent = new Intent();
            AddCourseActivity.currentCourse.addCourseInstructor(newInstructor);



            setResult(RESULT_OK, returnIntent);
            finish();
        }
        currentInstructor = null;
    }
}