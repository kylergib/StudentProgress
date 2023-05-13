package com.domain.studentprogress.add;


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
import androidx.test.espresso.matcher.ViewMatchers;

import com.domain.studentprogress.controller.add.AddAssessmentActivity;
import com.domain.studentprogress.controller.add.AddCourseActivity;
import com.domain.studentprogress.controller.add.AddInstructorActivity;
import com.domain.studentprogress.controller.add.AddNoteActivity;
import com.domain.studentprogress.controller.add.AddTermActivity;
import com.kgibs87.studentprogress.R;

import org.hamcrest.Matchers;
import org.junit.Test;

public class AddCourseActivityTest {

    @Test
    public void addAssessmentClickThenCancel() {
        ActivityScenario.launch(AddCourseActivity.class);
        Intents.init();
        testViewsExist();

        onView(withId(R.id.addAssessmentButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addAssessmentButton)).perform(click());

        intended(hasComponent(AddAssessmentActivity.class.getName()));

        onView(withTagValue(Matchers.is("cancelAssessmentButton"))).perform(click());

        testViewsExist();
        Intents.release();
    }
    @Test
    public void addInstructorClickThenCancel() {
        ActivityScenario.launch(AddCourseActivity.class);
        Intents.init();
        testViewsExist();

        onView(withId(R.id.addInstructorButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addInstructorButton)).perform(click());

        intended(hasComponent(AddInstructorActivity.class.getName()));
        onView(withTagValue(Matchers.is("cancelInstructorButton"))).perform(click());
        testViewsExist();
        Intents.release();
    }
    @Test
    public void addNoteClickThenCancel() {
        ActivityScenario.launch(AddCourseActivity.class);
        Intents.init();
        testViewsExist();

        onView(withId(R.id.addNoteButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addNoteButton)).perform(click());

        intended(hasComponent(AddNoteActivity.class.getName()));
        onView(withTagValue(Matchers.is("cancelNoteButton"))).perform(click());
        testViewsExist();
        Intents.release();
    }
    @Test
    public void cancelCourseClick() {
        ActivityScenario<AddTermActivity> scenario = ActivityScenario.launch(AddTermActivity.class);
        Intents.init();
        onView(withId(R.id.addCourseButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addCourseButton)).perform(click());

        onView(withTagValue(Matchers.is("cancelCourseButton"))).perform(click());

        onView(withId(R.id.termNameView))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.termNameView)).check(matches(isDisplayed()));
        Intents.release();
    }
    @Test
    public void addCourseClick() {
        ActivityScenario<AddTermActivity> scenario = ActivityScenario.launch(AddTermActivity.class);

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

    public void testViewsExist() {

        onView(withId(R.id.courseNameEditText)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.statusNameView)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.statusSpinner)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.assessments)).check(matches(ViewMatchers.isDisplayed()));

    }

}
