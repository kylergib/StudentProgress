package com.kgibs87.studentprogress.controller;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import static org.hamcrest.core.AllOf.allOf;

import com.kgibs87.studentprogress.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Before
    public void setUp() {

    }
    @After
    public void tearDown() {
    }

    @Test
    public void testViewsExist() {
        InstrumentationRegistry.getInstrumentation().getTargetContext()
                        .getSharedPreferences("preferences", Context.MODE_PRIVATE)
                                .edit().clear().apply();

        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.header_title)).check(matches(isDisplayed()));
        onView(withId(R.id.enterNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.nameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.submitButton)).check(matches(isDisplayed()));

    }


    @Test
    public void onCreatePreferences() {

        InstrumentationRegistry.getInstrumentation().getTargetContext()
                .getSharedPreferences("preferences", Context.MODE_PRIVATE)
                .edit().putString("name","Test").apply();

        Intents.init();
        ActivityScenario.launch(MainActivity.class);
        intended(hasComponent(DashboardActivity.class.getName()));
        Intents.release();

    }


    @Test
    public void submitName() {
        InstrumentationRegistry.getInstrumentation().getTargetContext()
                .getSharedPreferences("preferences", Context.MODE_PRIVATE)
                .edit().clear().apply();

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        Intents.init();
        scenario.onActivity(activity -> {
            EditText enterName =
                    activity.findViewById(R.id.nameEditText);
            enterName.setText("Testing");
        });
        onView(withId(R.id.submitButton)).perform(click());
        // Verify DashboardActivity started with test subject
        intended(allOf(
                hasComponent(DashboardActivity.class.getName())
        ));
        Intents.release();
    }

}