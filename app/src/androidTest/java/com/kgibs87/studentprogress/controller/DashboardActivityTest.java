package com.kgibs87.studentprogress.controller;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.controller.add.AddTermActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class DashboardActivityTest {

    @Rule
    public ActivityScenarioRule<DashboardActivity> activityRule = new ActivityScenarioRule<>(DashboardActivity.class);

    @Before
    public void setUp() {

        // Get a reference to the shared preferences file
        SharedPreferences sharedPreferences =
                InstrumentationRegistry.getInstrumentation().getTargetContext()
                        .getSharedPreferences("preferences", Context.MODE_PRIVATE);
        // Clear the shared preferences file
        sharedPreferences.edit().clear().apply();

        sharedPreferences.edit().putString("name", "Test").apply();
    }

    @Test
    public void testViewsExist() {
        onView(withId(R.id.welcomeText)).check(matches(ViewMatchers.isDisplayed()));

        //TODO: if recycler has data then statustext is not shown
//        onView(withId(R.id.termRecyclerView)).check(matches(ViewMatchers.isDisplayed())); //wont work if recycler view is blank
//        onView(withId(R.id.statusText)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.addTermButtonFragmentContainer)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void addTermClick() {
        Intents.init();
        onView(withTagValue(Matchers.is("addTermButton"))).perform(click());
        intended(hasComponent(AddTermActivity.class.getName())
        );
        onView(withId(R.id.courses)).check(matches(ViewMatchers.isDisplayed()));
//        onView(withId(R.id.courseRecyclerView)).check(matches(ViewMatchers.isDisplayed())); //wont work if recycler view is blank
        onView(withId(R.id.addCourseButton)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.startDate)).check(matches(ViewMatchers.isDisplayed()));

        //clicks cancel button to see if it goes back to previous activity correctly.
        onView(withTagValue(Matchers.is("cancelTermButton"))).perform(click());
        testViewsExist();
        Intents.release();
    }



}