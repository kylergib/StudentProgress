package com.kgibs87.studentprogress.controller;



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

import com.kgibs87.studentprogress.R;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class AddAssessmentActivityTest {


    @Test
    public void onCreate() {
        ActivityScenario.launch(AddAssessmentActivity.class);
        testViewsExist();
    }

    @Test
    public void addNoteTest() {
        ActivityScenario<AddCourseActivity> scenario = ActivityScenario.launch(AddCourseActivity.class);
        Intents.init();
        onView(withId(R.id.addAssessmentButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addAssessmentButton)).perform(click());
        testViewsExist();
        String testName = "Assessment: " + Math.random();
        onView(withId(R.id.assessmentNameEditText)).perform(typeText(testName));
        closeSoftKeyboard();

        onView(withTagValue(Matchers.is("saveAssessmentButton"))).perform(click());

        scenario.onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.assessmentsRecyclerView);
            boolean matchesNewAssessmentName = false;
            for (int i = 0; i < recyclerView.getChildCount(); i++) {

                TextView textView = (TextView) recyclerView.getChildAt(i);
                matchesNewAssessmentName = testName.equals(textView.getText().toString());
            }
            //TODO: check if note shows up on next view. not implemented in
            assertTrue(matchesNewAssessmentName);
        });
        Intents.release();
    }

    public void testViewsExist() {
        onView(withId(R.id.assessmentNameView)).check(matches(isDisplayed()));
        onView(withId(R.id.assessmentNameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.assessmentTypeView)).check(matches(isDisplayed()));
        onView(withId(R.id.assessmentTypeSpinner)).check(matches(isDisplayed()));
    }


}
