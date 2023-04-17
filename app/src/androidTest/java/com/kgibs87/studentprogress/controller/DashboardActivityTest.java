package com.kgibs87.studentprogress.controller;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.kgibs87.studentprogress.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class DashboardActivityTest {

    private SharedPreferences sharedPreferences;
    @Rule
    public ActivityTestRule<DashboardActivity> dashboardActivityTestRule =
            new ActivityTestRule<>(DashboardActivity.class,true,false);

    @Before
    public void setUp() {

        // Get a reference to the shared preferences file
        sharedPreferences =
                InstrumentationRegistry.getInstrumentation().getTargetContext()
                        .getSharedPreferences("preferences", Context.MODE_PRIVATE);
        // Clear the shared preferences file
        sharedPreferences.edit().clear().apply();

        sharedPreferences.edit().putString("name", "Test").apply();
    }

    @Test
    public void onCreate() {

        // Initialize Espresso-Intents
        Intents.init();

        // Start mainActivity
        dashboardActivityTestRule.launchActivity(null);
//        SharedPreferences sharedPref = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        // Verify DashboardActivity started with test subject
        intended(hasComponent(DashboardActivity.class.getName()));

        onView(withId(R.id.welcomeText))
                .check(matches(withText(String.format("Welcome, %s!", sharedPreferences.getString("name", null)))));

        // Must be called at end of each test case
        Intents.release();
    }

    @Test
    public void addTermClick() {

        // Initialize Espresso-Intents
        Intents.init();

        // Start mainActivity
        dashboardActivityTestRule.launchActivity(null);
//        SharedPreferences sharedPref = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        onView(withId(R.id.addTermButton)).perform(click());
        intended(hasComponent(TermDetailsActivity.class.getName())
        );


        // Must be called at end of each test case
        Intents.release();

    }
}