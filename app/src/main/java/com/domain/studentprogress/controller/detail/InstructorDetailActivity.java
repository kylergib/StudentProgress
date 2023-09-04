package com.domain.studentprogress.controller.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.model.Instructor;
import com.domain.studentprogress.model.StudentDatabase;
import com.domain.studentprogress.util.FormatStrings;
import com.domain.studentprogress.util.Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgibs87.studentprogress.R;

public class InstructorDetailActivity extends AppCompatActivity implements FloatingButtonFragment.OnButtonClickListener {
    private EditText instructorNameEditText;
    private EditText instructorNumberEditText;
    private EditText instructorEmailEditText;
    private Instructor currentInstructor;
    private boolean editMode;
    private boolean addMode;
    private Fragment backButtonFragment;
    private StudentDatabase mStudentDb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());

        instructorNameEditText = findViewById(R.id.instructorNameEditText);
        instructorNumberEditText = findViewById(R.id.instructorPhoneNumberEditText);
        instructorEmailEditText = findViewById(R.id.instructorEmailEditText);
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

        Intent intent = getIntent();
        int backButtonImage;
        if (intent.hasExtra("currentInstructor")) {
            addMode = false;
            editMode = false;
            currentInstructor = (Instructor) intent.getSerializableExtra("currentInstructor");
            TextView header = findViewById(R.id.headerTitleInstructor);
            header.setText(currentInstructor.getInstructorName());
            findViewById(R.id.instructorNameTextView).setVisibility(View.GONE);
            TextView numberView = findViewById(R.id.instructorNumberTextView);
            numberView.setText("Number: " + FormatStrings.formatPhoneNumber(currentInstructor.getInstructorPhoneNumber()));
            TextView emailView = findViewById(R.id.instructorEmailTextView);
            emailView.setText("E-mail: " + currentInstructor.getInstructorEmail());

            instructorEmailEditText.setVisibility(View.GONE);
            instructorNameEditText.setVisibility(View.GONE);
            instructorNumberEditText.setVisibility(View.GONE);
            backButtonImage = R.drawable.arrow_back;
            findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);
        } else {
            editMode = true;
            addMode = true;
            currentInstructor = new Instructor();
            backButtonImage = R.drawable.baseline_close;
        }





        backButtonFragment = fragmentManager.findFragmentById(R.id.backButtonFragmentContainer);
        if (backButtonFragment == null) {
            String cancelTag = "cancelInstructorButton";
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.START | Gravity.BOTTOM;
            backButtonFragment = new FloatingButtonFragment(cancelTag,params,backButtonImage);
            fragmentManager.beginTransaction()
                    .add(R.id.backButtonFragmentContainer, backButtonFragment,cancelTag)
                    .commit();
        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);



        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_1);
        if (editMode) menuItem.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_1:
                Log.d("TermActivity-onOptionsItemSelected", "editTerm()");
                if (!editMode) editInstructor();
                return true;
        }
        return false;
    }
    public void closeEdit() {
        TextView header = findViewById(R.id.headerTitleInstructor);
        header.setText(currentInstructor.getInstructorName());
        findViewById(R.id.instructorNameTextView).setVisibility(View.GONE);
        TextView numberView = findViewById(R.id.instructorNumberTextView);
        numberView.setText("Number: " + FormatStrings.formatPhoneNumber(currentInstructor.getInstructorPhoneNumber()));
        TextView emailView = findViewById(R.id.instructorEmailTextView);
        emailView.setText("E-mail: " + currentInstructor.getInstructorEmail());

        instructorEmailEditText.setVisibility(View.GONE);
        instructorNameEditText.setVisibility(View.GONE);
        instructorNumberEditText.setVisibility(View.GONE);
        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);
        FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelInstructorButton");
        testButton.setImageResource(R.drawable.arrow_back);
    }
    public void editInstructor() {
        editMode = false;
        ActionMenuItemView menuItem = findViewById(R.id.action_1);
        menuItem.setVisibility(View.GONE);
        FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelInstructorButton");
        testButton.setImageResource(R.drawable.baseline_close);
        findViewById(R.id.instructorNameTextView).setVisibility(View.VISIBLE);
        TextView numberView = findViewById(R.id.instructorNumberTextView);
        numberView.setText("Number");
        TextView emailView = findViewById(R.id.instructorEmailTextView);
        emailView.setText("E-mail");

        instructorEmailEditText.setVisibility(View.VISIBLE);
        instructorEmailEditText.setText(currentInstructor.getInstructorEmail());
        instructorNameEditText.setVisibility(View.VISIBLE);
        instructorNameEditText.setText(currentInstructor.getInstructorName());
        instructorNumberEditText.setVisibility(View.VISIBLE);
        instructorNumberEditText.setText(currentInstructor.getInstructorPhoneNumber());
        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.VISIBLE);
    }

    public void addInstructorSetup() {
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
    }

    @Override
    public void onButtonClick(View view, String tag) {
        if (tag.equals("cancelInstructorButton")) {
            if (addMode) {


                Log.d("Back tag", tag);
                currentInstructor = null;
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            } else if (editMode) {
                Log.d("CourseDetails-OnButtonClick", "cancel - else");
                closeEdit();
            } else {
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        } else if (tag.equals("saveInstructorButton")) {
            Log.d("Add tag", tag);
            boolean nameEmpty = instructorNameEditText.getText().toString().isEmpty();

            if (nameEmpty) {
                Toast.makeText(this, "Name cannot be empty",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String name = instructorNameEditText.getText().toString();
            String number = instructorNumberEditText.getText().toString();
            String email = instructorEmailEditText.getText().toString();
            currentInstructor.setInstructorName(name);
            currentInstructor.setInstructorEmail(email);
            currentInstructor.setInstructorPhoneNumber(number);

            Intent intent = getIntent();
            if (intent.hasExtra("isCurrentCourse")) {
                currentInstructor.setCourseID(CourseDetailsActivity.currentCourse.getId());
                if (addMode) {
                    long instructorId = mStudentDb.addInstructor(currentInstructor);
                    currentInstructor.setId(instructorId);
                    CourseDetailsActivity.currentCourse.addCourseInstructor(currentInstructor);
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    currentInstructor = null;
                    finish();
                } else {
                    CourseDetailsActivity.currentCourse.updateInstructor(currentInstructor);
                    mStudentDb.updateInstructor(currentInstructor);
                    closeEdit();

                    Helper.closeKeyboard(this,view);
                }
            } else if (addMode){
                CourseDetailsActivity.currentCourse.addCourseInstructor(currentInstructor);
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                currentInstructor = null;
                finish();
            } else {
                CourseDetailsActivity.currentCourse.addCourseInstructor(currentInstructor);
                closeEdit();
                Helper.closeKeyboard(this,view);
            }
        }
    }
}