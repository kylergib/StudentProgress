package com.kgibs87.studentprogress.controller;


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
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertTrue;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import com.kgibs87.studentprogress.R;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AddCourseActivityTest {

    @Test
    public void addAssessmentClick() {
        ActivityScenario.launch(AddCourseActivity.class);
        Intents.init();

        onView(withId(R.id.addAssessmentButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addAssessmentButton)).perform(click());

        intended(hasComponent(AddAssessmentActivity.class.getName()));
        Intents.release();
    }
    @Test
    public void addInstructorClick() {
        ActivityScenario.launch(AddCourseActivity.class);
        Intents.init();
        onView(withId(R.id.addInstructorButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addInstructorButton)).perform(click());

        intended(hasComponent(AddInstructorActivity.class.getName()));
        Intents.release();
    }
    @Test
    public void addNoteClick() {
        ActivityScenario.launch(AddCourseActivity.class);
        Intents.init();
        onView(withId(R.id.addNoteButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addNoteButton)).perform(click());

        intended(hasComponent(AddNoteActivity.class.getName()));
        Intents.release();
    }
    @Test
    public void cancelCourseClick() {
        ActivityScenario<TermActivity> scenario = ActivityScenario.launch(TermActivity.class);
        onView(withId(R.id.addCourseButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addCourseButton)).perform(click());
        Intents.init();
        onView(withTagValue(Matchers.is("cancelCourseButton"))).perform(click());

        onView(withId(R.id.termNameView))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.termNameView)).check(matches(isDisplayed()));
        Intents.release();
    }
    @Test
    public void addCourseClick() {
        ActivityScenario<TermActivity> scenario = ActivityScenario.launch(TermActivity.class);
        onView(withId(R.id.addCourseButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addCourseButton)).perform(click());

        String courseName = String.format("Test %f",Math.random());
        onView(withId(R.id.courseNameEditText)).perform(typeText(courseName));
        closeSoftKeyboard();

        onView(withTagValue(Matchers.is("saveCourseButton"))).perform(click());

        onView(withId(R.id.headerTitle)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.termNameView)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.termNameEditText)).check(matches(ViewMatchers.isDisplayed()));
        scenario.onActivity(activity -> {

            RecyclerView termRecyclerView = activity.findViewById(R.id.courseRecyclerView);
            boolean matchesNewCourseName = false;

            for (int i = 0; i < termRecyclerView.getChildCount(); i++) {

                TextView textView = (TextView) termRecyclerView.getChildAt(i);
                matchesNewCourseName = courseName.equals(textView.getText().toString());
            }
            assertTrue(matchesNewCourseName);
        });
    }

}
