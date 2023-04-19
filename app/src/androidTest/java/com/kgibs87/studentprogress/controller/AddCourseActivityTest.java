package com.kgibs87.studentprogress.controller;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import com.kgibs87.studentprogress.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AddCourseActivityTest {

    @Rule
    public ActivityTestRule<AddTermActivity> addTermActivityTestRule =
            new ActivityTestRule<>(AddTermActivity.class,true,false);

    @Before
    public void setUp() throws Exception {
        // Initialize Espresso-Intents
        Intents.init();

        // Start dashboardActivity
        addTermActivityTestRule.launchActivity(null);

        onView(withId(R.id.addCourseButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addCourseButton)).perform(click());
    }
    @After
    public void tearDown() {
        Intents.release();
    }
    @Test
    public void addAssessmentClick() {

        onView(withId(R.id.addAssessmentButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addAssessmentButton)).perform(click());

        intended(hasComponent(AddAssessmentActivity.class.getName()));
    }
    @Test
    public void addInstructorClick() {
        onView(withId(R.id.addInstructorButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addInstructorButton)).perform(click());

        intended(hasComponent(AddInstructorActivity.class.getName()));
    }
    @Test
    public void addNoteClick() {
        onView(withId(R.id.addNoteButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addNoteButton)).perform(click());

        intended(hasComponent(AddNoteActivity.class.getName()));
    }
    @Test
    public void cancelCourseClick() {
        onView(withId(R.id.cancelCourseButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.cancelCourseButton)).perform(click());
        onView(withId(R.id.termNameView))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.termNameView)).check(matches(isDisplayed()));

    }
    @Test
    public void addCourseClick() {
        //TODO: add test once i add the logic for the course prolly?
    }

}
