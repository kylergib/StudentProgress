package com.domain.studentprogress.controller.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.domain.studentprogress.adapter.CourseAdapter;
import com.domain.studentprogress.fragment.DateFragment;
import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.model.Course;
import com.domain.studentprogress.model.StudentDatabase;
import com.domain.studentprogress.util.Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.controller.DashboardActivity;
import com.domain.studentprogress.holder.CourseHolder;
import com.domain.studentprogress.model.Term;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TermDetailsActivity  extends AppCompatActivity implements DateFragment.OnDateSelectedListener,
        FloatingButtonFragment.OnButtonClickListener, CourseHolder.OnCourseClickListener {

    private static StudentDatabase mStudentDb ;
    private LocalDate startDate = LocalDate.now();
    public LocalDate endDate = LocalDate.now();
    public static final int COURSE_REQUEST_CODE = 1234;
    public static Term currentTerm;
    public static Term oldTerm;
    private Fragment backButtonFragment;
    private Fragment startDateFragment;
    private Fragment endDateFragment;
    private boolean editMode;
    private boolean addMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());
        setContentView(R.layout.activity_detail_term);

        Intent intent = getIntent();


        boolean intentHasTerm = intent.hasExtra("currentTerm");
        int cancelButtonImage;
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
            String saveTag = "saveTermButton";
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

        if (intentHasTerm) {
            currentTerm = (Term) intent.getSerializableExtra("currentTerm");
            oldTerm = (Term) intent.getSerializableExtra("currentTerm");
            addMode = false;
            setHeader();
            editMode = false;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, u");
            int smallTextSize = 15;
            TextView startDate = findViewById(R.id.startDate);
            String dateText = currentTerm.getStartDate().format(formatter) +
                    " - " + currentTerm.getEndDate().format(formatter);
            startDate.setText(dateText);
            startDate.setTextSize(smallTextSize);

            TextView endDate = findViewById(R.id.endDate);
            endDate.setVisibility(View.GONE);

            TextView termName = findViewById(R.id.termNameView);
            termName.setVisibility(View.GONE);

            EditText termNameInput = findViewById(R.id.termNameEditText);
            termNameInput.setVisibility(View.GONE);
            cancelButtonImage = R.drawable.arrow_back;

            findViewById(R.id.startDateFragmentContainer).setVisibility(View.GONE);
            findViewById(R.id.endDateFragmentContainer).setVisibility(View.GONE);
            findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);




        } else {
            addTermSetUp();
            cancelButtonImage = R.drawable.baseline_close;
            addMode = true;
        }
        setRecyclerCourse();



        backButtonFragment = fragmentManager.findFragmentById(R.id.backButtonFragmentContainer);
        if (backButtonFragment == null) {
            String cancelTag = "cancelTermButton";
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.START | Gravity.BOTTOM;
            backButtonFragment = new FloatingButtonFragment(cancelTag,params,cancelButtonImage);
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
                if (!editMode) editTerm();
                return true;
        }
        return false;
    }
    public void editTerm() {

        ActionMenuItemView menuItem = findViewById(R.id.action_1);
        menuItem.setVisibility(View.GONE);
        FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelTermButton");
        testButton.setImageResource(R.drawable.baseline_close);
        TextView startDate = findViewById(R.id.startDate);
        startDate.setText("Start Date");
        TextView endDate = findViewById(R.id.endDate);
        endDate.setVisibility(View.VISIBLE);
        TextView termName = findViewById(R.id.termNameView);
        termName.setVisibility(View.VISIBLE);
        EditText termNameInput = findViewById(R.id.termNameEditText);
        termNameInput.setVisibility(View.VISIBLE);
        termNameInput.setText(currentTerm.getName());
        addTermSetUp();

    }
    public void viewTerm() {
        setHeader();
        editMode = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, u");
        int smallTextSize = 15;
        TextView startDate = findViewById(R.id.startDate);
        String dateText = currentTerm.getStartDate().format(formatter) +
                " - " + currentTerm.getEndDate().format(formatter);
        startDate.setText(dateText);
        startDate.setTextSize(smallTextSize);

        TextView endDate = findViewById(R.id.endDate);
        endDate.setVisibility(View.GONE);

        TextView termName = findViewById(R.id.termNameView);
        termName.setVisibility(View.GONE);

        EditText termNameInput = findViewById(R.id.termNameEditText);
        termNameInput.setVisibility(View.GONE);

        FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelTermButton");
        testButton.setImageResource(R.drawable.arrow_back);
        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);

        findViewById(R.id.startDateFragmentContainer).setVisibility(View.GONE);
        findViewById(R.id.endDateFragmentContainer).setVisibility(View.GONE);
        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);

    }

    public void addTermSetUp() {
        Log.d("AddTermActivity", "starting set up");
        if (currentTerm == null) currentTerm = new Term();

        editMode = true;


        Button deleteTermButton = findViewById(R.id.deleteTermButton);
        deleteTermButton.setVisibility(View.GONE);





        if (currentTerm.getStartDate() != null) {
            DatePicker startDatePicker = startDateFragment.getView().findViewWithTag("startDate");
            startDatePicker.updateDate(currentTerm.getStartDate().getYear(),
                    currentTerm.getStartDate().getMonthValue()-1,currentTerm.getStartDate().getDayOfMonth());
        }
        findViewById(R.id.startDateFragmentContainer).setVisibility(View.VISIBLE);

        if (currentTerm.getEndDate() != null) {
            DatePicker endDatePicker = endDateFragment.getView().findViewWithTag("endDate");
            endDatePicker.updateDate(currentTerm.getEndDate().getYear(),
                    currentTerm.getEndDate().getMonthValue()-1,currentTerm.getEndDate().getDayOfMonth());
        }
        findViewById(R.id.endDateFragmentContainer).setVisibility(View.VISIBLE);
        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.VISIBLE);

    }


    public void setHeader() {
        TextView headerText = findViewById(R.id.headerTitle);
        headerText.setText(currentTerm.getName());

    }
    public void setRecyclerCourse() {
        if (currentTerm.getTermCourses().size() == 0) return;
        Log.d("termDetail", String.valueOf(currentTerm.getTermCourses().size()));
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        int colSize;
        if (currentTerm.getTermCourses().size() > 1) colSize = 2;
        else colSize = 1;
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(this, colSize);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CourseAdapter(currentTerm.getTermCourses(),this));
    }

    public void addCourseClick(View view) {
        Log.d("Debug-teg", "addCourseClicked");
        Log.d("AddTermActivity", String.valueOf(currentTerm));
        Intent courseIntent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
        Log.d("TermActivity", String.format("Term ID: %s", currentTerm.getId()));
        if (currentTerm.getId() > 0) courseIntent.putExtra("currentTerm", true);
        startActivityForResult(courseIntent,COURSE_REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("termDetailsActivity Result", resultCode + " - " + requestCode);
        if (requestCode == COURSE_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("AddTermActivity", "returning to activity");
            Log.d("AddTermActivity", String.valueOf(currentTerm));

        }
        setRecyclerCourse();
    }

    @Override
    public void onDateSelected(LocalDate localDate, String tag) {
        if (tag.equals("startDate")) {
            Log.d("AddNoteActivity", "Start date selected: " + localDate.toString());
            startDate = localDate;
        } else if (tag.equals("endDate")) {
            Log.d("AddNoteActivity", tag + " selected: " + localDate.toString());
            endDate = localDate;
        }
    }

    @Override
    public void onButtonClick(View view, String tag) {
        Log.d("AddTermActivity", tag);
        if (tag.equals("cancelTermButton")) {



            if (editMode && !addMode) {
                editMode = false;
                findViewById(R.id.action_1).setVisibility(View.VISIBLE);
                viewTerm();
                Button deleteTermButton = findViewById(R.id.deleteTermButton);
                deleteTermButton.setVisibility(View.VISIBLE);
                return;
            }

            currentTerm = null;
            finish();
        } else if (tag.equals("saveTermButton") && addMode) {
            saveTerm();

        } else if (tag.equals("saveTermButton")) {

            if (!updateTermObject()) {
                return;
            }
            editMode = false;
            viewTerm();
            Button deleteTermButton = findViewById(R.id.deleteTermButton);
            deleteTermButton.setVisibility(View.VISIBLE);
            setInputs();
            Helper.closeKeyboard(this,view);
            //TODO: save changes to term
            mStudentDb.updateTerm(currentTerm);
        }

    }

    public boolean updateTermObject() {
        Log.d("TermActivity-UpdateTermObject()", "updateTermObject");
        if (!checkInputs()) return false;
        EditText termNameEditText = findViewById(R.id.termNameEditText);
        String termNameString = termNameEditText.getText().toString();
        currentTerm.setName(termNameString);
        currentTerm.setStartDate(startDate);
        currentTerm.setEndDate(endDate);
        Log.d("TermActivity-UpdateTermObject()", "endUpdateTerm");
        return true;
    }

    public void setInputs() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, u");
        TextView startDate = findViewById(R.id.startDate);
        String dateText = currentTerm.getStartDate().format(formatter) +
                " - " + currentTerm.getEndDate().format(formatter);
        startDate.setText(dateText);
        setHeader();
    }

    public boolean checkInputs() {
        EditText termNameEditText = findViewById(R.id.termNameEditText);
        String termNameString = termNameEditText.getText().toString();
        boolean endBeforeStart = endDate.isBefore(startDate);
        boolean startEqualsEnd = startDate.isEqual(endDate);
        boolean termNameEmpty = (termNameString.isEmpty());

        if (endBeforeStart) {
            Log.d("AddTermActivity", "Start date is not before the end date.");
            Toast.makeText(TermDetailsActivity.this, "Start date is not before the end date.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (startEqualsEnd) {
            Log.d("AddTermActivity", "Start date and end date cannot be the same");
            Toast.makeText(TermDetailsActivity.this, "Start date and end date cannot be the same",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (termNameEmpty) {
            Log.d("AddTermActivity", "Term name cannot be empty");
            Toast.makeText(TermDetailsActivity.this, "Term name cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void  saveTerm() {

        Log.d("AddTermActivity", "returning to activity");
        Log.d("AddTermActivity", String.valueOf(currentTerm));

        EditText termNameEditText = findViewById(R.id.termNameEditText);
        String termNameString = termNameEditText.getText().toString();

        if (!checkInputs()) {
            return;
        }
        currentTerm.setName(termNameString);
        currentTerm.setStartDate(startDate);
        currentTerm.setEndDate(endDate);
        Log.d("Add Term Tag", String.format("%s - %s - %s",
                currentTerm.getName(), currentTerm.getStartDate().toString(),
                currentTerm.getEndDate().toString()));

        long termId = mStudentDb.addTerm(currentTerm);
        Log.d("AddTermTest", String.valueOf(termId));
        currentTerm.setId(termId);

        boolean courseListNotEmpty = !currentTerm.getTermCourses().isEmpty();


        if (courseListNotEmpty) {
            currentTerm.getTermCourses().forEach(course -> {
                boolean assessmentListNotEmpty = !course.getCourseAssessments().isEmpty();
                boolean instructorListNotEmpty = !course.getCourseInstructors().isEmpty();
                boolean noteListNotEmpty = !course.getCourseNotes().isEmpty();
                course.setTermID(termId);
                long courseId = mStudentDb.addCourse(course);

                if (courseId < 1) return;

                Log.d("Add Term:", "Added course with ID: " + courseId);
                course.setId(courseId);

                Log.d("Course:", course.getCourseName());
                if (assessmentListNotEmpty) {
                    course.getCourseAssessments().forEach(assessment -> {
                        assessment.setCourseID(courseId);
                        long assessmentId = mStudentDb.addAssessment(assessment);
                        Log.d("Add Term:", "Added assessment with ID: " + assessmentId);
                        assessment.setAssessmentId(assessmentId);

                    });
                } else {
                    Log.d("Add term", "Assessment List is empty");
                }
                if (instructorListNotEmpty) {
                    course.getCourseInstructors().forEach(instructor -> {
                        instructor.setCourseID(courseId);
                        long instructorId = mStudentDb.addInstructor(instructor);
                        Log.d("Add Term:", "Added instructor with ID: " + instructorId);
                        instructor.setId(instructorId);
                    });
                } else {
                    Log.d("Add term", "Instructor List is empty");
                }
                if (noteListNotEmpty) {
                    course.getCourseNotes().forEach(note -> {
                        note.setCourseID(courseId);
                        long noteID = mStudentDb.addNote(note);
                        Log.d("Add Term:", "Added note with ID: " + noteID);
                        note.setId(noteID);
                    });
                } else {
                    Log.d("Add term", "Note List is empty");
                }
            });
        } else {
            Log.d("Add term", "Course List is empty");
        }


        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        startActivity(dashboardIntent);
        currentTerm = null;

    }

    @Override
    public void onCourseClick(View view, Course course) {
        Log.d("onCourseClick", "onCourseClick: " + course.getCourseName());
        Log.d("onCourseClick", String.valueOf(course.getCourseAssessments()));
        Intent intent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
        intent.putExtra("currentCourse", course);
        if (currentTerm.getId() > 0) intent.putExtra("currentTerm", true);
        startActivityForResult(intent,COURSE_REQUEST_CODE);


    }


    public void deleteTermClick(View view) {

        if (currentTerm.getTermCourses().size() > 0) {
            Log.w("deleteTermClick","Cannot delete term because it has a course. ");
            Toast.makeText(TermDetailsActivity.this, "Cannot delete a term if it has a course in it. Please delete course first.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("TermDetails", "Delete: " + currentTerm.getId());
        boolean deletedTermBool = mStudentDb.deleteTerm(currentTerm.getId());
        if (deletedTermBool) {
            Log.d("deleteTermClick", "Successfully deleted " + currentTerm.getId());
            Intent dashboardIntent = new Intent(this, DashboardActivity.class);
            startActivity(dashboardIntent);
            currentTerm = null;
        }
        else {
            Log.d("deleteTermClick", "Could not delete " + currentTerm.getId());
            Toast.makeText(TermDetailsActivity.this, "There was an error deleting term, please try again",
                    Toast.LENGTH_SHORT).show();
        }


    }


}
