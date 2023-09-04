package com.domain.studentprogress.detail;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import static org.junit.Assert.assertTrue;

import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.domain.studentprogress.controller.detail.CourseDetailsActivity;
import com.domain.studentprogress.controller.detail.InstructorDetailActivity;
import com.kgibs87.studentprogress.R;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InstructorActivityTest {


    @Test
    public void onCreate() {
        try (ActivityScenario<InstructorDetailActivity> ignored = ActivityScenario.launch(InstructorDetailActivity.class)) {
            testViewsExist();
        }
    }
    @Test
    public void saveInstructor() {

        ActivityScenario<CourseDetailsActivity> scenario = ActivityScenario.launch(CourseDetailsActivity.class);

        onView(withId(R.id.addInstructorButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addInstructorButton)).perform(click());
        testViewsExist();


        String name = String.format("name %f",Math.random());
        String number = String.format("name %f",Math.random());
        String email = String.format("name %f",Math.random());
        onView(withId(R.id.instructorNameEditText)).perform(typeText(name));
        onView(withId(R.id.instructorPhoneNumberEditText)).perform(typeText(number));
        onView(withId(R.id.instructorEmailEditText)).perform(typeText(email));
        closeSoftKeyboard();
        onView(withTagValue(Matchers.is("saveInstructorButton"))).perform(click());
        scenario.onActivity(activity -> {
            RecyclerView instructorRecyclerView = activity.findViewById(R.id.instructorsRecyclerView);
            boolean matchesNewInstructorName = false;

            for (int i = 0; i < instructorRecyclerView.getChildCount(); i++) {

                CardView cardView = (CardView) instructorRecyclerView.getChildAt(i);
                TextView textView = cardView.findViewById(R.id.instructorNameTextView);
                matchesNewInstructorName = name.equals(textView.getText().toString());
            }
            assertTrue(matchesNewInstructorName);
        });



    }

    public void testViewsExist() {
        onView(withId(R.id.headerTitleInstructor)).check(matches(isDisplayed()));
        onView(withId(R.id.instructorNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.instructorNameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.instructorNumberTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.instructorPhoneNumberEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.instructorEmailTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.instructorEmailEditText)).check(matches(isDisplayed()));
    }
//    }

}
