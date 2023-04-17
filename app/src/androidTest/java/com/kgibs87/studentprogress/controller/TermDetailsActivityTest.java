package com.kgibs87.studentprogress.controller;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.kgibs87.studentprogress.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TermDetailsActivityTest {

    @Rule
    public ActivityTestRule<TermDetailsActivity> termActivityTestRule =
            new ActivityTestRule<>(TermDetailsActivity.class,true,false);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
        // Initialize Espresso-Intents
        Intents.init();

        // Start mainActivity
        termActivityTestRule.launchActivity(null);
//        SharedPreferences sharedPref = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        // Verify DashboardActivity started with test subject
        intended(hasComponent(TermDetailsActivity.class.getName()));

        // Must be called at end of each test case
        Intents.release();
    }

    @Test
    public void backButtonClick() {
        // Initialize Espresso-Intents
        Intents.init();

        // Start mainActivity
        termActivityTestRule.launchActivity(null);
//        SharedPreferences sharedPref = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        onView(withId(R.id.backButton)).perform(click());
        intended(hasComponent(DashboardActivity.class.getName())
        );


        // Must be called at end of each test case
        Intents.release();
    }
}