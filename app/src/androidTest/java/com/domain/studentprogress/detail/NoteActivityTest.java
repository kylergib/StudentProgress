package com.domain.studentprogress.detail;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import static org.junit.Assert.assertTrue;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.domain.studentprogress.controller.detail.NoteDetailActivity;
import com.domain.studentprogress.controller.detail.CourseDetailsActivity;
import com.kgibs87.studentprogress.R;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class NoteActivityTest {


    @Test
    public void onCreate() {

        try (ActivityScenario<NoteDetailActivity> ignored = ActivityScenario.launch(NoteDetailActivity.class)) {
            testViewsExist();
        }

    }

    @Test
    public void addNoteTest() {
        ActivityScenario<CourseDetailsActivity> scenario = ActivityScenario.launch(CourseDetailsActivity.class);
        Intents.init();
        onView(withId(R.id.addNoteButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addNoteButton)).perform(click());
        testViewsExist();
        String testNote = "This is a test note: " + Math.random();
        onView(withId(R.id.note_edit_text)).perform(typeText(testNote));
        closeSoftKeyboard();
        onView(withTagValue(Matchers.is("saveNoteButton"))).perform(click());

        scenario.onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.notesRecyclerView);
            boolean matchesNewNoteName = false;

            for (int i = 0; i < recyclerView.getChildCount(); i++) {

                TextView textView = (TextView) recyclerView.getChildAt(i);
                matchesNewNoteName = testNote.equals(textView.getText().toString());
            }
            assertTrue(matchesNewNoteName);
        });
        Intents.release();
    }

    public void testViewsExist() {
        onView(withId(R.id.noteHeader)).check(matches(isDisplayed()));
        onView(withId(R.id.note_edit_text)).check(matches(isDisplayed()));
    }


}
