package com.kgibs87.studentprogress;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import static org.hamcrest.core.AllOf.allOf;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FreshInstallTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class,true,false);


    @Test
    public void freshInstallTestSubmit() {

        // Get a reference to the shared preferences file
        SharedPreferences sharedPreferences =
                InstrumentationRegistry.getInstrumentation().getTargetContext()
                        .getSharedPreferences("preferences", Context.MODE_PRIVATE);
        // Clear the shared preferences file
        sharedPreferences.edit().clear().apply();
        // Initialize Espresso-Intents
        Intents.init();


        // Start mainActivity
        mainActivityActivityTestRule.launchActivity(null);

        EditText enterName =
                mainActivityActivityTestRule.getActivity().findViewById(R.id.nameEditText);
        enterName.setText("Testing");

        // Click on submit button
        onView(withId(R.id.submitButton)).perform(click());

        // Verify QuestionActivity started with test subject
        intended(allOf(
                hasComponent(DashboardActivity.class.getName())
        ));

        // Must be called at end of each test case
        Intents.release();

    }

}

