package com.kgibs87.studentprogress.controller;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.fragment.FloatingButtonFragment;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class AddTermActivityTest {

    @Rule
    public ActivityTestRule<AddTermActivity> addTermActivityTestRule =
            new ActivityTestRule<>(AddTermActivity.class,true,false);

    @Rule
    public ActivityTestRule<DashboardActivity> dashboardActivityTestRule =
            new ActivityTestRule<>(DashboardActivity.class,true,false);

    @Before
    public void setUp() throws Exception {

        // Initialize Espresso-Intents
        Intents.init();

        // Start dashboardActivity
        dashboardActivityTestRule.launchActivity(null);
        // clicks button to add term
        onView(withTagValue(Matchers.is((Object) "addTermButton"))).perform(click());


    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void addCourseClick() {
    }

    @Test
    public void cancelTermClick() {


        onView(withTagValue(Matchers.is((Object) "cancelTermButton"))).perform(click());


        onView(withId(R.id.welcomeText)).check(matches(isDisplayed()));





    }

    @Test
    public void addTermClick() {


        onView(withTagValue(Matchers.is((Object) "saveTermButton"))).perform(click());

        onView(withId(R.id.welcomeText)).check(matches(isDisplayed()));
        //TODO: add check that new term is showing on dashboard now

    }
}