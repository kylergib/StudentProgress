package com.domain.studentprogress.controller.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.domain.studentprogress.adapter.AssessmentAdapter;
import com.domain.studentprogress.controller.DashboardActivity;
import com.domain.studentprogress.fragment.DateFragment;
import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.holder.AssessmentHolder;
import com.domain.studentprogress.model.Assessment;
import com.domain.studentprogress.model.Course;
import com.domain.studentprogress.model.Instructor;
import com.domain.studentprogress.model.Note;
import com.domain.studentprogress.model.StudentDatabase;
import com.domain.studentprogress.model.Term;
import com.domain.studentprogress.util.Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.adapter.InstructorAdapter;
import com.domain.studentprogress.adapter.NoteAdapter;
import com.domain.studentprogress.holder.InstructorHolder;
import com.domain.studentprogress.holder.NoteHolder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity  implements DateFragment.OnDateSelectedListener,
        FloatingButtonFragment.OnButtonClickListener, AssessmentHolder.OnAssessmentClickListener,
        InstructorHolder.OnInstrcutorClickListener, NoteHolder.OnNoteClickListener {
    private static StudentDatabase mStudentDb ;
    private EditText courseNameEditText;
    private String courseStatus;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now();
    public static final int ASSESSMENT_REQUEST_CODE = 2345;
    public static final int INSTRUCTOR_REQUEST_CODE = 2346;
    public static final int NOTE_REQUEST_CODE = 2347;
    public static Course currentCourse;
    private String activityTag = "CourseDetailsActivity";
    private boolean editMode;
    private boolean addMode;
    private Fragment backButtonFragment;
    private Fragment startDateFragment;
    private Fragment endDateFragment;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_course);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
        mHandler = new Handler();

        Intent intent = getIntent();

        boolean intentHasCourse = intent.hasExtra("currentCourse");
        int backButtonImage;

        if (intentHasCourse) {
            editMode = false;
            addMode = false;
            currentCourse = (Course) intent.getSerializableExtra("currentCourse");
            TextView header = findViewById(R.id.headerTitleCourse);
            header.setText(currentCourse.getCourseName());

            TextView statusTextView = findViewById(R.id.statusNameView);
            statusTextView.setText("Status: " + currentCourse.getCourseStatus());

            TextView startDate = findViewById(R.id.startDate);
            startDate.setVisibility(View.GONE);

            TextView endDate = findViewById(R.id.endDate);
            endDate.setVisibility(View.GONE);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, u");
            TextView courseName = findViewById(R.id.courseNameView);
            String dateText = currentCourse.getStartDate().format(formatter) +
                    " - " + currentCourse.getEndDate().format(formatter);
            courseName.setText(dateText);

            courseNameEditText = findViewById(R.id.courseNameEditText);
            courseNameEditText.setVisibility(View.GONE);

            Spinner spinner = findViewById(R.id.statusSpinner);
            spinner.setVisibility(View.GONE);

            Log.d("CourseDetailsActivity", String.valueOf(currentCourse.getCourseAssessments()));
            updateAssessments();
            updateInstructors();
            updateNotes();
            backButtonImage = R.drawable.arrow_back;
            findViewById(R.id.startDateFragmentContainer).setVisibility(View.GONE);
            findViewById(R.id.endDateFragmentContainer).setVisibility(View.GONE);

            findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);


        } else {
            addMode = true;
            editMode = true;
            currentCourse = new Course();
            courseNameEditText = findViewById(R.id.courseNameEditText);
            backButtonImage = R.drawable.baseline_close;
            Button deleteButton = findViewById(R.id.deleteCourseButton);
            deleteButton.setVisibility(View.GONE);
        }
        addCourseSetUp();

        updateAssessments();





        FragmentManager fragmentManager = getSupportFragmentManager();
        backButtonFragment = fragmentManager.findFragmentById(R.id.backButtonFragmentContainer);
        if (backButtonFragment == null) {
            String cancelTag = "cancelCourseButton";
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
            SubMenu subMenu = menu.addSubMenu(Menu.NONE, 1, Menu.NONE, "Add Notification");
            subMenu.add(Menu.NONE, 2, Menu.NONE, "Start Date Notification");
            subMenu.add(Menu.NONE, 3, Menu.NONE, "End Date Notification");
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
        System.out.println(item.getItemId());
        switch (item.getItemId()) {

            case R.id.action_1:
                Log.d("TermActivity-onOptionsItemSelected", "editTerm()");
                if (!editMode) editCourse();
                return true;
            case 2:
                addCourseToAlarm(true);
                System.out.println("in 2");
                return true;
            case 3:
                addCourseToAlarm(false);
                System.out.println("in 3");
                return true;
        }
        return false;
    }

    public void addCourseToAlarm(boolean isStartDate) {
        String notificationString;
        String toastString;

        ZoneId sysZoneId = ZoneId.systemDefault();
        Instant instant;

        if (isStartDate) {
            notificationString = String.format("Course: %s has started - %s", currentCourse.getCourseName(), currentCourse.getCourseStartDate().toString());
            toastString = "start";
            instant = currentCourse.getStartDate().atStartOfDay(sysZoneId).toInstant();
        } else {
            notificationString = String.format("Course: %s ended - %s", currentCourse.getCourseName(), currentCourse.getCourseEndDate().toString());
            toastString = "end";
            instant = currentCourse.getEndDate().atStartOfDay(sysZoneId).toInstant();
        }
        System.out.println(notificationString);
        Date date = Date.from(instant);
        DashboardActivity.sendNotification(getApplicationContext(),notificationString,date.getTime());
        Toast.makeText(this,String.format("%s Date Notification added",toastString), Toast.LENGTH_SHORT).show();
    }

    public void closeEdit() {
        editMode = false;
        FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelCourseButton");
        testButton.setImageResource(R.drawable.arrow_back);

        TextView header = findViewById(R.id.headerTitleCourse);
        header.setText(currentCourse.getCourseName());

        TextView statusTextView = findViewById(R.id.statusNameView);
        statusTextView.setText("Status: " + currentCourse.getCourseStatus());

        TextView startDate = findViewById(R.id.startDate);
        startDate.setVisibility(View.GONE);

        TextView endDate = findViewById(R.id.endDate);
        endDate.setVisibility(View.GONE);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, u");
        TextView courseName = findViewById(R.id.courseNameView);
        String dateText = currentCourse.getStartDate().format(formatter) +
                " - " + currentCourse.getEndDate().format(formatter);
        courseName.setText(dateText);

        courseNameEditText = findViewById(R.id.courseNameEditText);
        courseNameEditText.setVisibility(View.GONE);

        findViewById(R.id.startDateFragmentContainer).setVisibility(View.GONE);
        findViewById(R.id.endDateFragmentContainer).setVisibility(View.GONE);

        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);
    }
    public void editCourse() {

        ActionMenuItemView menuItem = findViewById(R.id.action_1);
        menuItem.setVisibility(View.GONE);

        FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelCourseButton");
        testButton.setImageResource(R.drawable.baseline_close);

        TextView statusTextView = findViewById(R.id.statusNameView);
        statusTextView.setText("Status");

        TextView startDate = findViewById(R.id.startDate);
        startDate.setVisibility(View.VISIBLE);

        TextView endDate = findViewById(R.id.endDate);
        endDate.setVisibility(View.VISIBLE);

        TextView courseName = findViewById(R.id.courseNameView);
        courseName.setText("Course Name");

        courseNameEditText = findViewById(R.id.courseNameEditText);
        courseNameEditText.setVisibility(View.VISIBLE);

        Spinner spinner = findViewById(R.id.statusSpinner);
        spinner.setVisibility(View.VISIBLE);
        EditText courseNameEditText = findViewById(R.id.courseNameEditText);
        courseNameEditText.setText(currentCourse.getCourseName());
        editCourseSetUp();





    }
    public void editCourseSetUp() {
        Log.d("courseDetails - editCourseSetUp", "starting edit");
        editMode = true;
        if (currentCourse.getStartDate() != null && startDateFragment != null) {
            DatePicker startDatePicker = startDateFragment.getView().findViewWithTag("startDate");
            startDatePicker.updateDate(currentCourse.getStartDate().getYear(),
                    currentCourse.getStartDate().getMonthValue()-1,currentCourse.getStartDate().getDayOfMonth());

        }

        if (currentCourse.getEndDate() != null && endDateFragment != null) {
            DatePicker endDatePicker = endDateFragment.getView().findViewWithTag("endDate");
            endDatePicker.updateDate(currentCourse.getEndDate().getYear(),
                    currentCourse.getEndDate().getMonthValue()-1,currentCourse.getEndDate().getDayOfMonth());

        }
        findViewById(R.id.startDateFragmentContainer).setVisibility(View.VISIBLE);
        findViewById(R.id.endDateFragmentContainer).setVisibility(View.VISIBLE);
        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.VISIBLE);
        Log.d("courseDetails - editCourseSetUp", "ending edit");
    }
    public void addCourseSetUp() {



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
            String saveTag = "saveCourseButton";
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

        List<String> statusList = Arrays.asList("in progress", "completed", "dropped", "plan to take");
        HashMap<String, Integer> statusMap = new HashMap<String, Integer>() {{
            put("in progress",0);
            put("completed",1);
            put("dropped",2);
            put("plan to take",3);
        }};
        Spinner spinner = findViewById(R.id.statusSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Integer spinnerValue = statusMap.get(currentCourse.getStatus());
        if (currentCourse.getStatus() != null && spinnerValue != null) spinner.setSelection(spinnerValue);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selected item here
                courseStatus = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing here
            }
        });

    }

    public void addAssessmentClick(View view) {
        Intent assessmentIntent = new Intent(getApplicationContext(), AssessmentDetailActivity.class);
        if (currentCourse.getId() > 0) assessmentIntent.putExtra("isCurrentCourse", true);
        startActivityForResult(assessmentIntent,ASSESSMENT_REQUEST_CODE);

    }
    public void addInstructorClick(View view) {
        Intent instructorIntent = new Intent(getApplicationContext(), InstructorDetailActivity.class);
        if (currentCourse.getId() > 0) instructorIntent.putExtra("isCurrentCourse", true);
        startActivityForResult(instructorIntent,INSTRUCTOR_REQUEST_CODE);

    }
    public void addNoteClick(View view) {
        Intent noteIntent = new Intent(getApplicationContext(), NoteDetailActivity.class);
        if (currentCourse.getId() > 0) noteIntent.putExtra("isCurrentCourse", true);
        startActivityForResult(noteIntent,NOTE_REQUEST_CODE);
    }

    public void updateAssessments() {
        Log.d("CourseDetailsActivity", String.valueOf(currentCourse.getCourseAssessments()));
        RecyclerView courseRecyclerView = findViewById(R.id.assessmentsRecyclerView);
        int colSize;
        if (currentCourse.getCourseAssessments().size() > 1) colSize = 2;
        else colSize = 1;
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(this,colSize);

        courseRecyclerView.setLayoutManager(layoutManager);
        courseRecyclerView.setAdapter(new AssessmentAdapter(currentCourse.getCourseAssessments(), this));
    }

    public void updateInstructors() {
        RecyclerView recyclerView = findViewById(R.id.instructorsRecyclerView);
        int colSize;
        if (currentCourse.getCourseInstructors().size() > 1) colSize = 2;
        else colSize = 1;
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(this, colSize);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new InstructorAdapter(currentCourse.getCourseInstructors(), this));
    }

    public void updateNotes() {
        RecyclerView recyclerView = findViewById(R.id.notesRecyclerView);

        int colSize;
        if (currentCourse.getCourseNotes().size() > 1) colSize = 2;
        else colSize = 1;
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(this,colSize);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new NoteAdapter(currentCourse.getCourseNotes(), this));
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ASSESSMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            updateAssessments();
        }
        else if (requestCode == INSTRUCTOR_REQUEST_CODE && resultCode == RESULT_OK) {
            updateInstructors();
        }
        else if (requestCode == NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            updateNotes();
        }

    }
    @Override
    public void onDateSelected(LocalDate localDate, String tag) {
        if (tag.equals("startDate")) {
            Log.d("AddCourseActivity", "Start date selected: " + localDate.toString());
            startDate = localDate;
        } else if (tag.equals("endDate")) {
            Log.d("AddCourseActivity", "End date selected: " + localDate.toString());
            endDate = localDate;
        }
    }

    @Override
    public void onButtonClick(View view, String tag) {
        Log.d("CourseDetails-OnButtonClick", "Starting onbuttonCLick");
        if (tag.equals("cancelCourseButton")) {
            Log.d("CourseDetails-OnButtonClick", "cencelCourseButton");
            if (addMode) {
                Log.d("CourseDetails-OnButtonClick", "cancel - addmode");
                Log.d("Back tag", tag);

                currentCourse = null;
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            } else if (editMode) {
                Log.d("CourseDetails-OnButtonClick", "cancel - else");
                TermDetailsActivity.currentTerm.updateTermCourse(currentCourse);
                closeEdit();
            } else {
                System.out.println(currentCourse.getCourseName());
                System.out.println(currentCourse.getCourseStatus());
                TermDetailsActivity.currentTerm.updateTermCourse(currentCourse);
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        } else if (tag.equals("saveCourseButton")) {
            Log.d("Add tag", tag);
            //TODO: create term object and add to sqlite
            if (String.valueOf(courseNameEditText.getText()).equals("")) {
                Toast.makeText(this, "Course name cannot be blank",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (startDate.isAfter(endDate)) {
                Toast.makeText(this, "Start date is not before the end date.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Intent returnIntent = new Intent();
            currentCourse.setCourseName(String.valueOf(courseNameEditText.getText()));
            currentCourse.setCourseStartDate(startDate);
            currentCourse.setCourseEndDate(endDate);
            currentCourse.setCourseStatus(courseStatus);
            Intent intent = getIntent();
            if (intent.hasExtra("currentTerm")) {
                //TODO: save to database if term already exists
                Log.d("CourseActivity", "term already exists");
                if (addMode) {
                    currentCourse.setTermID(TermDetailsActivity.currentTerm.getId());
                    long courseId = mStudentDb.addCourse(currentCourse);
                    currentCourse.setId(courseId);
                    TermDetailsActivity.currentTerm.addTermCourse(currentCourse);
                    Log.d("CourseDetailActivity", "trying to save");
                    currentCourse = null;
                    addMode = false;
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    mStudentDb.updateCourse(currentCourse);
                    Log.d("CourseDetailActivity", "trying to update");
                    TermDetailsActivity.currentTerm.updateTermCourse(currentCourse);
                    closeEdit();
                    Helper.closeKeyboard(this,view);
                }



            } else {
                Log.d("CourseActivity", "term doesn't exist");
                if (addMode) {
                    TermDetailsActivity.currentTerm.addTermCourse(currentCourse);
                    currentCourse = null;
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    TermDetailsActivity.currentTerm.updateTermCourse(currentCourse);
                    currentCourse = null;
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
//            }
        }

    }

    @Override
    public void onAssessmentClick(View view, Assessment assessment) {
        Intent intent = new Intent(getApplicationContext(), AssessmentDetailActivity.class);
        intent.putExtra("currentAssessment", assessment);
        if (currentCourse.getId() > 0) intent.putExtra("isCurrentCourse", true);
        startActivityForResult(intent,ASSESSMENT_REQUEST_CODE);


    }

    @Override
    public void onInstructorClick(View view, Instructor instructor) {
        Intent intent = new Intent(getApplicationContext(), InstructorDetailActivity.class);
        intent.putExtra("currentInstructor", instructor);
        if (currentCourse.getId() > 0) intent.putExtra("isCurrentCourse", true);
        startActivityForResult(intent,INSTRUCTOR_REQUEST_CODE);
    }


    @Override
    public void onNoteClick(View view, Note note) {
        Intent intent = new Intent(getApplicationContext(), NoteDetailActivity.class);
        intent.putExtra("currentNote", note);
        if (currentCourse.getId() > 0) intent.putExtra("isCurrentCourse", true);
        startActivityForResult(intent,NOTE_REQUEST_CODE);
    }


    public void deleteCourseClick(View view) {
        //TODO: add logic to delete course
        Log.d(activityTag, String.format("Trying to delete course: %s, id: %s",
                currentCourse.getCourseName(), currentCourse.getId()));
        boolean deleteCourseBool = mStudentDb.deleteCourse(currentCourse.getId());
        if (deleteCourseBool) {
            Log.d("deleteCourseClick", "Successfully deleted " + currentCourse.getId());
            TermDetailsActivity.currentTerm.removeTermCourse(currentCourse);
            currentCourse = null;

            setResult(RESULT_OK);
            finish();

        }
        else Log.d("deleteTermClick", "Could not delete " + currentCourse.getId());
    }

    public void exportNotesClick(View view) {
        //TODO: add note
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Course Notes");
        String exportNotes = currentCourse.getCourseName() + " Notes: ";
        int numNotes = 0;
        for (Note courseNote : currentCourse.getCourseNotes()) {
            numNotes += 1;
            exportNotes += "\nNote " + numNotes + ": " + courseNote.getMessage();
        }
        intent.putExtra(Intent.EXTRA_TEXT, exportNotes);
        if (intent.resolveActivity(getPackageManager()) != null) {
            Intent chooser = intent.createChooser(intent, "Share Notes");
            startActivity(chooser);
        }
    }
}