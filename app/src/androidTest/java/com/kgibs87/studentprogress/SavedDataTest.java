package com.kgibs87.studentprogress;




import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import static org.hamcrest.core.AllOf.allOf;

import com.kgibs87.studentprogress.view.DashboardActivity;
import com.kgibs87.studentprogress.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SavedDataTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class,true,false);

    @Rule
    public ActivityTestRule<DashboardActivity> dashboardActivityTestRule =
            new ActivityTestRule<>(DashboardActivity.class,true,false);

    @Test
    public void openAppToDashboard() {
        // Initialize Espresso-Intents
        Intents.init();

        // Start mainActivity
        mainActivityTestRule.launchActivity(null);

        // Verify QuestionActivity started with test subject
        intended(allOf(
                hasComponent(DashboardActivity.class.getName())
        ));

        // Must be called at end of each test case
        Intents.release();

    }

    //checks if welcome text is set properly.
    @Test
    public void welcomeTextSetTest() {
        // Initialize Espresso-Intents
        Intents.init();

        // Start mainActivity
        dashboardActivityTestRule.launchActivity(null);
        SharedPreferences sharedPref = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        // Verify QuestionActivity started with test subject
        intended(allOf(
                hasComponent(DashboardActivity.class.getName())
        ));

        onView(ViewMatchers.withId(R.id.welcomeText))
                .check(matches(withText(String.format("Welcome, %s!", sharedPref.getString("name", null)))));

        // Must be called at end of each test case
        Intents.release();
    }


}