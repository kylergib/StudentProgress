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

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.model.Term;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

import java.time.LocalDate;


@RunWith(AndroidJUnit4.class)
public class TermActivityTest {

    @Rule
    public ActivityScenarioRule<TermActivity> activityRule = new ActivityScenarioRule<>(TermActivity.class);


    @Test
    public void testViewsExist() {
        onView(withId(R.id.headerTitle)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.termNameView)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.termNameEditText)).check(matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.courses)).check(matches(ViewMatchers.isDisplayed()));
//        onView(withId(R.id.courseRecyclerView)).check(matches(ViewMatchers.isDisplayed())); //wont work if recycler view is blank
        onView(withId(R.id.addCourseButton)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.startDate)).check(matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.endDate)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.backButtonFragmentContainer)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.addButtonFragmentContainer)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void addTermClick() {
        activityRule.getScenario().onActivity(activity -> {
            activity.endDate = LocalDate.now().plusMonths(1);
        });

        String termName = String.format("Test %f",Math.random());
        onView(withId(R.id.termNameEditText)).perform(typeText(termName));
        closeSoftKeyboard();
        onView(withTagValue(Matchers.is("saveTermButton"))).perform(click());
        onView(withId(R.id.welcomeText)).check(matches(isDisplayed()));
        ActivityScenario<DashboardActivity> scenario = ActivityScenario.launch(DashboardActivity.class);
        scenario.onActivity(activity -> {

            RecyclerView termRecyclerView = activity.findViewById(R.id.termRecyclerView);
            boolean matchesNewTermName = false;

            for (int i = 0; i < termRecyclerView.getChildCount(); i++) {

                TextView textView = (TextView) termRecyclerView.getChildAt(i);
                matchesNewTermName = termName.equals(textView.getText().toString());
            }
            assertTrue(matchesNewTermName);
        });




    }
}