package com.kgibs87.studentprogress.controller.add;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
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

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.controller.add.AddCourseActivity;
import com.kgibs87.studentprogress.controller.add.AddNoteActivity;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class AddNoteActivityTest {


    @Test
    public void onCreate() {
        ActivityScenario.launch(AddNoteActivity.class);
        testViewsExist();
    }

    @Test
    public void addNoteTest() {
        ActivityScenario<AddCourseActivity> scenario = ActivityScenario.launch(AddCourseActivity.class);
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
