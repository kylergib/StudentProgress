package com.domain.studentprogress.controller.detail;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.model.Note;
import com.domain.studentprogress.model.StudentDatabase;
import com.domain.studentprogress.util.Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgibs87.studentprogress.R;

public class NoteDetailActivity extends AppCompatActivity implements FloatingButtonFragment.OnButtonClickListener {
    private EditText messageEditText;
    private Note currentNote;
    private StudentDatabase mStudentDb ;
    private boolean editMode;
    private boolean addMode;
    private Fragment backButtonFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        messageEditText = findViewById(R.id.noteEditText);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mStudentDb = StudentDatabase.getInstance(getApplicationContext());


        Intent intent = getIntent();
        int backButtonImage;
        Fragment addButtonFragment = fragmentManager.findFragmentById(R.id.addButtonFragmentContainer);
        if (addButtonFragment == null) {
            String saveTag = "saveNoteButton";
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


        if (intent.hasExtra("currentNote")) {
            editMode = false;
            addMode = false;
            currentNote = (Note) intent.getSerializableExtra("currentNote");
            TextView header = findViewById(R.id.noteHeader);
            header.setText(currentNote.getMessage());

            messageEditText.setVisibility(View.GONE);
            backButtonImage = R.drawable.arrow_back;
            findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);

        } else {
            currentNote = new Note();
            backButtonImage = R.drawable.baseline_close;
            editMode = true;
            addMode = true;

        }



        backButtonFragment = fragmentManager.findFragmentById(R.id.backButtonFragmentContainer);
        if (backButtonFragment == null) {
            String cancelTag = "cancelNoteButton";
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
                if (!editMode) editNote();
                return true;
        }
        return false;
    }
    public void editNote() {
        editMode = true;

        messageEditText.setVisibility(View.VISIBLE);
        messageEditText.setText(currentNote.getMessage());
        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.VISIBLE);
        FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelNoteButton");
        testButton.setImageResource(R.drawable.baseline_close);

    }
    public void closeEdit() {
        editMode = false;
        TextView header = findViewById(R.id.noteHeader);
        header.setText(currentNote.getMessage());

        messageEditText.setVisibility(View.GONE);
        findViewById(R.id.addButtonFragmentContainer).setVisibility(View.GONE);
        FloatingActionButton testButton = backButtonFragment.getView().findViewWithTag("cancelNoteButton");
        testButton.setImageResource(R.drawable.arrow_back);

    }

    @Override
    public void onButtonClick(View view, String tag) {
        Log.d("Note-onButtonClick", tag);
        if (tag.equals("cancelNoteButton")) {
            if (addMode){
                Log.d("Back tag", tag);
                currentNote = null;
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
        } else if (tag.equals("saveNoteButton")) {
            Log.d("Add tag", tag);
            //TODO: create term object and add to sqlite
            boolean messageIsEmpty = messageEditText.getText().toString().isEmpty();
            if (messageIsEmpty) {
                Toast.makeText(this, "Message cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            String message = messageEditText.getText().toString();
            currentNote.setMessage(message);
            Intent intent = getIntent();
            if (intent.hasExtra("isCurrentCourse")) {
                currentNote.setCourseID(CourseDetailsActivity.currentCourse.getId());
                if (addMode) {
                    long noteId = mStudentDb.addNote(currentNote);
                    currentNote.setId(noteId);
                    CourseDetailsActivity.currentCourse.addCourseNote(currentNote);
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    currentNote = null;
                    finish();
                } else {
                    CourseDetailsActivity.currentCourse.updateNote(currentNote);
                    mStudentDb.updateNote(currentNote);
                    closeEdit();

                    Helper.closeKeyboard(this,view);
                }
            } else if (addMode){
                CourseDetailsActivity.currentCourse.addCourseNote(currentNote);
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                currentNote = null;
                finish();
            } else {
                CourseDetailsActivity.currentCourse.addCourseNote(currentNote);
                closeEdit();
                Helper.closeKeyboard(this,view);
            }
        }
    }
}