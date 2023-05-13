package com.domain.studentprogress.controller.add;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.domain.studentprogress.fragment.FloatingButtonFragment;
import com.domain.studentprogress.model.Note;
import com.kgibs87.studentprogress.R;

public class AddNoteActivity extends AppCompatActivity implements FloatingButtonFragment.OnButtonClickListener {
    private Note currentNote;
    private EditText messageEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        messageEditText = findViewById(R.id.note_edit_text);

        FragmentManager fragmentManager = getSupportFragmentManager();

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

        Fragment backButtonFragment = fragmentManager.findFragmentById(R.id.backButtonFragmentContainer);
        if (backButtonFragment == null) {
            String cancelTag = "cancelNoteButton";
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
        if (tag.equals("cancelNoteButton")) {
            Log.d("Back tag", tag);
            finish();
        } else if (tag.equals("saveNoteButton")) {
            Log.d("Add tag", tag);
            //TODO: create term object and add to sqlite
            boolean messageIsEmpty = messageEditText.getText().toString().isEmpty();
            if (messageIsEmpty) {
                Toast.makeText(this, "Message cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            String message = messageEditText.getText().toString();
            Note newNote = new Note(message);
            Intent returnIntent = new Intent();
            AddCourseActivity.currentCourse.addCourseNote(newNote);


            setResult(RESULT_OK, returnIntent);
            finish();
        }
        currentNote = null;
    }
}