package com.kgibs87.studentprogress.controller.add;

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

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.kgibs87.studentprogress.R;
import com.kgibs87.studentprogress.controller.add.AddCourseActivity;
import com.kgibs87.studentprogress.controller.add.AddInstructorActivity;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddInstructorActivityTest {


    @Test
    public void onCreate() {
        ActivityScenario.launch(AddInstructorActivity.class);
        testViewsExist();
    }
    @Test
    public void saveInstructor() {

        ActivityScenario<AddCourseActivity> scenario = ActivityScenario.launch(AddCourseActivity.class);

        onView(withId(R.id.addInstructorButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addInstructorButton)).perform(click());
        testViewsExist();


        String name = String.format("name %f",Math.random());
        String number = String.format("name %f",Math.random());
        String email = String.format("name %f",Math.random());
        onView(withId(R.id.instructorNameEditText)).perform(typeText(name));
        onView(withId(R.id.instructorNumberEditText)).perform(typeText(number));
        onView(withId(R.id.instructorEmailEditText)).perform(typeText(email));
        closeSoftKeyboard();
        onView(withTagValue(Matchers.is("saveInstructorButton"))).perform(click());
        scenario.onActivity(activity -> {
            RecyclerView instructorRecyclerView = activity.findViewById(R.id.instructorsRecyclerView);
            boolean matchesNewInstructorName = false;

            for (int i = 0; i < instructorRecyclerView.getChildCount(); i++) {

                TextView textView = (TextView) instructorRecyclerView.getChildAt(i);
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
        onView(withId(R.id.instructorNumberEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.instructorEmailTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.instructorEmailEditText)).check(matches(isDisplayed()));
    }
//    @Rule
//    public ActivityTestRule<AddInstructorActivity> addInstructorActivity =
//            new ActivityTestRule<>(AddInstructorActivity.class,true,false);
//    @Rule
//    public ActivityTestRule<AddCourseActivity> addCourseActivity =
//            new ActivityTestRule<>(AddCourseActivity.class,true,false);
//
//    @Before
//    public void setUp() throws Exception {
//
//
//    }
//
//    @After
//    public void tearDown() {
//
//    }
//
//    @Test
//    public void onCreate() {
//        // Initialize Espresso-Intents
//        Intents.init();
//
//        // Start activity
//        addInstructorActivity.launchActivity(null);
//        intended(hasComponent(AddInstructorActivity.class.getName()));
//        Intents.release();
//    }
//    @Test
//    public void onButtonClickCancel() {
//        // Initialize Espresso-Intents
//        Intents.init();
//
//        // Start activity
//        addCourseActivity.launchActivity(null);
//        intended(hasComponent(AddCourseActivity.class.getName()));
//
//        onView(withId(R.id.addInstructorButton))
//                .perform(ViewActions.scrollTo());
//        onView(withId(R.id.addInstructorButton)).perform(click());
//
//        intended(hasComponent(AddInstructorActivity.class.getName()));
//        onView(withId(R.id.headerTitleInstructor)).check(matches(isDisplayed()));
//        onView(withTagValue(Matchers.is("cancelInstructorButton"))).perform(click());
//        intended(hasComponent(AddCourseActivity.class.getName()));
//        onView(withId(R.id.headerTitleCourse))
//                .perform(ViewActions.scrollTo());
//        onView(withId(R.id.headerTitleCourse)).check(matches(isDisplayed()));
//        Intents.release();
//    }
//
//    @Test
//    public void onButtonClickSave() {
//
//        //TODO: this test fails because fields cannot be left blank, fix please
//
//        // Initialize Espresso-Intents
//        Intents.init();
//
//        // Start activity
//        addCourseActivity.launchActivity(null);
//        intended(hasComponent(AddCourseActivity.class.getName()));
//
//        onView(withId(R.id.addInstructorButton))
//                .perform(ViewActions.scrollTo());
//        onView(withId(R.id.addInstructorButton)).perform(click());
//
//        intended(hasComponent(AddInstructorActivity.class.getName()));
//        onView(withId(R.id.headerTitleInstructor)).check(matches(isDisplayed()));
//        onView(withTagValue(Matchers.is("saveInstructorButton"))).perform(click());
//        //TODO: add logic to make sure it was added correctly once i add the database
//        intended(hasComponent(AddCourseActivity.class.getName()));
//        onView(withId(R.id.headerTitleCourse))
//                .perform(ViewActions.scrollTo());
//        onView(withId(R.id.headerTitleCourse)).check(matches(isDisplayed()));
//        Intents.release();
//    }

}
