package com.kgibs87.studentprogress.controller;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;

import android.util.Log;
import android.widget.TextView;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.kgibs87.studentprogress.R;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddInstructorActivityTest {
    @Rule
    public ActivityTestRule<AddInstructorActivity> addInstructorActivity =
            new ActivityTestRule<>(AddInstructorActivity.class,true,false);
    @Rule
    public ActivityTestRule<AddCourseActivity> addCourseActivity =
            new ActivityTestRule<>(AddCourseActivity.class,true,false);

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() {

    }

    @Test
    public void onCreate() {
        // Initialize Espresso-Intents
        Intents.init();

        // Start activity
        addInstructorActivity.launchActivity(null);
        intended(hasComponent(AddInstructorActivity.class.getName()));
        Intents.release();
    }
    @Test
    public void onButtonClickCancel() {
        // Initialize Espresso-Intents
        Intents.init();

        // Start activity
        addCourseActivity.launchActivity(null);
        intended(hasComponent(AddCourseActivity.class.getName()));

        onView(withId(R.id.addInstructorButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addInstructorButton)).perform(click());

        intended(hasComponent(AddInstructorActivity.class.getName()));
        onView(withId(R.id.headerTitleInstructor)).check(matches(isDisplayed()));
        onView(withTagValue(Matchers.is("cancelInstructorButton"))).perform(click());
        intended(hasComponent(AddCourseActivity.class.getName()));
        onView(withId(R.id.headerTitleCourse))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.headerTitleCourse)).check(matches(isDisplayed()));
        Intents.release();
    }

    @Test
    public void onButtonClickSave() {

        //TODO: this test fails because fields cannot be left blank, fix please

        // Initialize Espresso-Intents
        Intents.init();

        // Start activity
        addCourseActivity.launchActivity(null);
        intended(hasComponent(AddCourseActivity.class.getName()));

        onView(withId(R.id.addInstructorButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addInstructorButton)).perform(click());

        intended(hasComponent(AddInstructorActivity.class.getName()));
        onView(withId(R.id.headerTitleInstructor)).check(matches(isDisplayed()));
        onView(withTagValue(Matchers.is("saveInstructorButton"))).perform(click());
        //TODO: add logic to make sure it was added correctly once i add the database
        intended(hasComponent(AddCourseActivity.class.getName()));
        onView(withId(R.id.headerTitleCourse))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.headerTitleCourse)).check(matches(isDisplayed()));
        Intents.release();
    }

}
