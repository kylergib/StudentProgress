package com.domain.studentprogress.controller.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.domain.studentprogress.controller.DashboardActivity;
import com.domain.studentprogress.controller.MainActivity;
import com.domain.studentprogress.fragment.DateFragment;
import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.model.Assessment;
import com.domain.studentprogress.model.StudentDatabase;
import com.domain.studentprogress.util.Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgibs87.studentprogress.R;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AssessmentDetailActivity  extends AppCompatActivity implements DateFragment.OnDateSelectedListener, FloatingButtonFragment.OnButtonClickListener {
    private EditText assessmentNameEditText;
    private StudentDatabase mStudentDb ;
    private LocalDate startDate;
    private LocalDate endDate;
    private String assessmentType;
    private static Assessment currentAssessment;
    private boolean editMode;
    private boolean addMode;
    private Fragment startDateFragment;
    private Fragment endDateFragment;
    private Fragment backButtonFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());


        Intent intent = getIntent();
        FragmentManager fragmentManager = getSupportFragmentManager();

        int backButtonImage;
        boolean intentHasAssessment = intent.hasExtra("currentAssessment");
        addAssessmentSetup();
        if (intentHasAssessment) {
            editMode = false;
            addMode = false;
            currentAssessment = (Assessment) intent.getSerializableExtra("currentAssessment");
            TextView headerText = findViewById(R.id.headerTitle);
            headerText.setText(currentAssessment.getAssessmentTitle());


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, u");
            TextView courseName = findViewById(R.id.courseNameView);
            String dateText = currentAssessment.getAssessmentStartDate().format(formatter) +
                    " - " + currentAssessment.getAssessmentEndDate().format(formatter);
            TextView assessmentNameTextView = findViewById(R.id.assessmentNameView);
            assessmentNameTextView.setText(dateText);

            assessmentNameEditText = findViewById(R.id.assessmentNameEditText);
            assessmentNameEditText.setVisibility(View.GONE);

            TextView startView = findViewById(R.id.startDate);
            startView.setVisibility(View.GONE);

            View startEditText = findViewById(R.id.startDateFragmentContainer);
            startEditText.setVisibility(View.GONE);

            TextView endView = findViewById(R.id.endDate);
            endView.setVisibility(View.GONE);

            View endEditText = findViewById(R.id.endDateFragmentContainer);
            endEditText.setVisibility(View.GONE);
            TextView typeTextView = findViewById(R.id.assessmentTypeView);
            typeTextView.setText("Assessment Type: " + currentAssessment.getAssessmentType());
            Spinner spinner = findViewById(R.id.assessmentTypeSpinner);
            spinner.setVisibility(View.GONE);
            findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);

            backButtonImage = R.drawable.arrow_back;

        }
        else {
            editMode = true;
            addMode = true;
            startDate = LocalDate.now();
            endDate = LocalDate.now();
            currentAssessment = new Assessment();
            assessmentNameEditText = findViewById(R.id.assessmentNameEditText);

            backButtonImage = R.drawable.baseline_close;

        }

        backButtonFragment = fragmentManager.findFragmentById(R.id.backButtonFragmentContainer);
        if (backButtonFragment == null) {
            String cancelTag = "cancelAssessmentButton";
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
        if (!addMode) {
            SubMenu subMenu = menu.addSubMenu(Menu.NONE, 4, Menu.NONE, "Add Notification");
            subMenu.add(Menu.NONE, 5, Menu.NONE, "Start Date Notification");
            subMenu.add(Menu.NONE, 6, Menu.NONE, "End Date Notification");
        }


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
                if (!editMode) editAssessment();
                editMode = true;
                return true;
            case 5:
                addAssessmentToAlarm(true);
                return true;
            case 6:
                addAssessmentToAlarm(false);
                return true;
        }
        return false;
    }
    public void addAssessmentToAlarm(boolean isStartDate) {
        String notificationString;
        String toastString;

        ZoneId sysZoneId = ZoneId.systemDefault();
        Instant instant;

        if (isStartDate) {
            notificationString = String.format("Assessment: %s is today - %s", currentAssessment.getAssessmentTitle(), currentAssessment.getAssessmentStartDate().toString());
            toastString = "start";
            instant = currentAssessment.getAssessmentStartDate().atStartOfDay(sysZoneId).toInstant();
        } else {
            notificationString = String.format("Assessment: %s is due today - %s", currentAssessment.getAssessmentTitle(), currentAssessment.getAssessmentEndDate().toString());
            toastString = "end";
            instant = currentAssessment.getAssessmentEndDate().atStartOfDay(sysZoneId).toInstant();
        }
        Date date = Date.from(instant);
        DashboardActivity.sendNotification(getApplicationContext(),notificationString,date.getTime());
        System.out.println(notificationString);
        Toast.makeText(this,String.format("%s Date Notification added",toastString), Toast.LENGTH_SHORT).show();
        MainActivity.addNumNotification();
    }
    public void closeEdit() {
        editMode = false;
        TextView headerText = findViewById(R.id.headerTitle);
        headerText.setText(currentAssessment.getAssessmentTitle());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, u");
        TextView courseName = findViewById(R.id.courseNameView);
        String dateText = currentAssessment.getAssessmentStartDate().format(formatter) +
                " - " + currentAssessment.getAssessmentEndDate().format(formatter);
        TextView assessmentNameTextView = findViewById(R.id.assessmentNameView);
        assessmentNameTextView.setText(dateText);

        assessmentNameEditText = findViewById(R.id.assessmentNameEditText);
        assessmentNameEditText.setVisibility(View.GONE);

        TextView startView = findViewById(R.id.startDate);
        startView.setVisibility(View.GONE);

        View startEditText = findViewById(R.id.startDateFragmentContainer);
        startEditText.setVisibility(View.GONE);

        TextView endView = findViewById(R.id.endDate);
        endView.setVisibility(View.GONE);

        View endEditText = findViewById(R.id.endDateFragmentContainer);
        endEditText.setVisibility(View.GONE);
        TextView typeTextView = findViewById(R.id.assessmentTypeView);
        typeTextView.setText("Assessment Type: " + currentAssessment.getAssessmentType());
        Spinner spinner = findViewById(R.id.assessmentTypeSpinner);
        spinner.setVisibility(View.GONE);
        if (backButtonFragment.getView() != null) {
            FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelAssessmentButton");
            testButton.setImageResource(R.drawable.arrow_back);
        }
        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);
    }
    public void editAssessment() {
        TextView headerText = findViewById(R.id.headerTitle);
        headerText.setText(currentAssessment.getAssessmentTitle());


        TextView assessmentNameTextView = findViewById(R.id.assessmentNameView);
        assessmentNameTextView.setText("Assessment Title");

        assessmentNameEditText = findViewById(R.id.assessmentNameEditText);
        assessmentNameEditText.setVisibility(View.VISIBLE);
        assessmentNameEditText.setText(currentAssessment.getAssessmentTitle());

        TextView startView = findViewById(R.id.startDate);
        startView.setVisibility(View.VISIBLE);

        View startEditText = findViewById(R.id.startDateFragmentContainer);
        startEditText.setVisibility(View.VISIBLE);

        TextView endView = findViewById(R.id.endDate);
        endView.setVisibility(View.VISIBLE);

        View endEditText = findViewById(R.id.endDateFragmentContainer);
        endEditText.setVisibility(View.VISIBLE);
        TextView typeTextView = findViewById(R.id.assessmentTypeView);
        typeTextView.setText("Assessment Type: " + currentAssessment.getAssessmentType());
        Spinner spinner = findViewById(R.id.assessmentTypeSpinner);
        spinner.setVisibility(View.VISIBLE);
        if (currentAssessment.getAssessmentStartDate() != null && startDateFragment != null) {
            DatePicker startDatePicker = startDateFragment.getView().findViewWithTag("startDate");
            startDatePicker.updateDate(currentAssessment.getAssessmentStartDate().getYear(),
                    currentAssessment.getAssessmentStartDate().getMonthValue()-1,currentAssessment.getAssessmentStartDate().getDayOfMonth());

        }

        if (currentAssessment.getAssessmentEndDate() != null && endDateFragment != null) {
            DatePicker endDatePicker = endDateFragment.getView().findViewWithTag("endDate");
            endDatePicker.updateDate(currentAssessment.getAssessmentEndDate().getYear(),
                    currentAssessment.getAssessmentEndDate().getMonthValue()-1,currentAssessment.getAssessmentEndDate().getDayOfMonth());

        }
        FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelAssessmentButton");
        testButton.setImageResource(R.drawable.baseline_close);
        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.VISIBLE);
    }

    public void addAssessmentSetup() {

        String[] statusList = {"Performance", "Objective"};

        Spinner spinner = findViewById(R.id.assessmentTypeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                assessmentType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing here
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        startDateFragment = fragmentManager.findFragmentById(R.id.startDateFragmentContainer);
        if (startDateFragment == null) {
            String tag = "startDate";
            startDateFragment = new DateFragment(tag);
            fragmentManager.beginTransaction()
                    .add(R.id.startDateFragmentContainer, startDateFragment,tag)
                    .commit();
        }

        endDateFragment = fragmentManager.findFragmentById(R.id.endDateFragmentContainer);
        if (endDateFragment == null) {
            String endTag = "endDate";
            endDateFragment = new DateFragment(endTag);


            fragmentManager.beginTransaction()
                    .add(R.id.endDateFragmentContainer, endDateFragment,endTag)
                    .commit();
        }

        Fragment addButtonFragment = fragmentManager.findFragmentById(R.id.addButtonFragmentContainer);
        if (addButtonFragment == null) {
            String saveTag = "saveAssessmentButton";
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
    public void onDateSelected(LocalDate localDate, String tag) {
        if (tag.equals("startDate")) {
            startDate = localDate;
            Log.d("AddAssessmentActivity", "Start date selected: " + localDate.toString());
        } else if (tag.equals("endDate")) {
            endDate = localDate;
            Log.d("AddAssessmentActivity", "End date selected: " + localDate.toString());
        }
    }

    @Override
    public void onButtonClick(View view, String tag) {
        if (tag.equals("cancelAssessmentButton")) {
            Log.d("Editmode?", String.valueOf(editMode));
            if (addMode) {
                Log.d("Back tag", tag);
                currentAssessment = null;
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            } else if (editMode) {
                closeEdit();
            } else {
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }

        } else if (tag.equals("saveAssessmentButton")) {
            String assessmentName = String.valueOf(assessmentNameEditText.getText());
            if (assessmentName.equals("")) {
                Toast.makeText(AssessmentDetailActivity.this, "Assessment name cannot be blank",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (startDate.isAfter(endDate)) {
                Toast.makeText(AssessmentDetailActivity.this, "Start date has to be on or before end date.",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            currentAssessment.setAssessmentTitle(assessmentName);
            currentAssessment.setAssessmentStartDate(startDate);
            currentAssessment.setAssessmentEndDate(endDate);
            currentAssessment.setAssessmentType(assessmentType);



            Intent intent = getIntent();
            if (intent.hasExtra("isCurrentCourse")) {
                currentAssessment.setCourseID( CourseDetailsActivity.currentCourse.getId());
                if (addMode) {

                    long assessmentID = mStudentDb.addAssessment(currentAssessment);
                    currentAssessment.setAssessmentId(assessmentID);
                    CourseDetailsActivity.currentCourse.addCourseAssessment(currentAssessment);
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    currentAssessment = null;
                    finish();
                } else {

                    CourseDetailsActivity.currentCourse.updateCourseAssessment(currentAssessment);

                    mStudentDb.updateAssessment(currentAssessment);
                    closeEdit();

                    Helper.closeKeyboard(this,view);

                }

            } else if (addMode) {
                CourseDetailsActivity.currentCourse.addCourseAssessment(currentAssessment);
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                currentAssessment = null;
                finish();
            } else {
                CourseDetailsActivity.currentCourse.updateCourseAssessment(currentAssessment);
                closeEdit();
                Helper.closeKeyboard(this,view);
            }
        }

    }


}