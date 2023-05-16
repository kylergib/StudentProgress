package com.domain.studentprogress.add;


import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import com.domain.studentprogress.controller.detail.CourseDetailsActivity;
import com.domain.studentprogress.controller.detail.TermDetailsActivity;
import com.domain.studentprogress.model.Assessment;
import com.domain.studentprogress.model.Course;
import com.domain.studentprogress.model.Instructor;
import com.domain.studentprogress.model.Note;
import com.kgibs87.studentprogress.R;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddCourseActivityTest {

    @Test
    public void addAssessmentClickThenCancel() {
        ActivityScenario.launch(CourseDetailsActivity.class);
        Intents.init();
        testViewsExist();

        onView(withId(R.id.addAssessmentButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addAssessmentButton)).perform(click());

        onView(withTagValue(Matchers.is("cancelAssessmentButton"))).perform(click());

        testViewsExist();
        Intents.release();
    }
    @Test
    public void addInstructorClickThenCancel() {
        ActivityScenario.launch(CourseDetailsActivity.class);
        Intents.init();
        testViewsExist();

        onView(withId(R.id.addInstructorButton))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.addInstructorButton)).perform(click());
        onView(withTagValue(Matchers.is("cancelInstructorButton"))).perform(click());
        testViewsExist();
        Intents.release();
    }
    @Test
    public void addNoteClickThenCancel() {
        ActivityScenario.launch(CourseDetailsActivity.class);
        Intents.init();
        testViewsExist();

        onView(withId(R.id.addNoteButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addNoteButton)).perform(click());
        onView(withTagValue(Matchers.is("cancelNoteButton"))).perform(click());
        testViewsExist();
        Intents.release();
    }
    @Test
    public void cancelCourseClick() {
        ActivityScenario<TermDetailsActivity> scenario = ActivityScenario.launch(TermDetailsActivity.class);
        Intents.init();
        onView(withId(R.id.addCourseButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addCourseButton)).perform(click());

        onView(withTagValue(Matchers.is("cancelCourseButton"))).perform(click());

        onView(withId(R.id.termNameView))
                .perform(ViewActions.scrollTo());
        onView(withId(R.id.termNameView)).check(matches(isDisplayed()));
        Intents.release();
    }
    @Test
    public void addCourseClick() {
        ActivityScenario<TermDetailsActivity> scenario = ActivityScenario.launch(TermDetailsActivity.class);

        onView(withId(R.id.addCourseButton))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.addCourseButton)).perform(click());

        String courseName = String.format("Test %f",Math.random());
        onView(withId(R.id.courseNameEditText)).perform(typeText(courseName));
        closeSoftKeyboard();

        onView(withTagValue(Matchers.is("saveCourseButton"))).perform(click());

        onView(withId(R.id.headerTitle)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.termNameView)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.termNameEditText)).check(matches(ViewMatchers.isDisplayed()));
        scenario.onActivity(activity -> {

            RecyclerView termRecyclerView = activity.findViewById(R.id.courseRecyclerView);
            boolean matchesNewCourseName = false;

            for (int i = 0; i < termRecyclerView.getChildCount(); i++) {

                TextView textView = (TextView) termRecyclerView.getChildAt(i);
                matchesNewCourseName = courseName.equals(textView.getText().toString());
            }
            assertTrue(matchesNewCourseName);
        });
    }

    @Test
    public void testFakeData() {
        Course newCourse = new Course("Test Course", LocalDate.of(2023,1,1),
                LocalDate.of(2023,1,2),"plan to take");
        Assessment newAssessment = new Assessment("Assessment Test", LocalDate.of(2023,1,1),
                LocalDate.of(2023,1,1),"performance");
        Note newNote = new Note("this is a message");
        Instructor newInstructor = new Instructor("Instructor name",
                "7652151070","testemail@gmail.com");
        newCourse.addCourseAssessment(newAssessment);
        newCourse.addCourseNote(newNote);
        newCourse.addCourseInstructor(newInstructor);


        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), CourseDetailsActivity.class);
        intent.putExtra("currentCourse", newCourse);

        ActivityScenario<CourseDetailsActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {

            //check if assessments are showing correctly in the recycler view
            RecyclerView assessmentRecycler = activity.findViewById(R.id.assessmentsRecyclerView);
            boolean assessmentsMatch = false;
            List<String> assessmentNames = new ArrayList<>();

            newCourse.getCourseAssessments().forEach(assessment ->
                assessmentNames.add(assessment.getAssessmentTitle())
            );
            assertEquals(assessmentRecycler.getChildCount(),newCourse.getCourseAssessments().size());
            for (int i = 0; i < assessmentRecycler.getChildCount(); i++) {

                TextView textView = (TextView) assessmentRecycler.getChildAt(i);
                assessmentsMatch = assessmentNames.contains(textView.getText());
                assertTrue(assessmentsMatch);
                assessmentsMatch = false;
            }

            //check if notes are showing correctly in the recycler view
            RecyclerView noteRecycler = activity.findViewById(R.id.notesRecyclerView);
            boolean notesMatch = false;
            List<String> noteMessages = new ArrayList<>();

            newCourse.getCourseNotes().forEach(note ->
                    noteMessages.add(note.getMessage())
            );
            assertEquals(noteRecycler.getChildCount(),newCourse.getCourseNotes().size());
            for (int i = 0; i < noteRecycler.getChildCount(); i++) {

                TextView textView = (TextView) noteRecycler.getChildAt(i);
                notesMatch = noteMessages.contains(textView.getText());
                assertTrue(notesMatch);
                notesMatch = false;
            }

            //check if instructors are showing correctly in the recycler view
            RecyclerView instructorRecycler = activity.findViewById(R.id.instructorsRecyclerView);
            boolean instructorMatch = false;
            List<String> instructorNames = new ArrayList<>();

            newCourse.getCourseInstructors().forEach(instructor ->
                    instructorNames.add(instructor.getInstructorName())
            );
            assertEquals(instructorRecycler.getChildCount(), newCourse.getCourseInstructors().size());
            for (int i = 0; i < instructorRecycler.getChildCount(); i++) {

                TextView textView = (TextView) instructorRecycler.getChildAt(i);
                instructorMatch = instructorNames.contains(textView.getText());
                assertTrue(instructorMatch);
                instructorMatch = false;
            }



        });


        onView(withId(R.id.headerTitleCourse)).check(matches(withText(newCourse.getCourseName())));
        onView(withId(R.id.statusSpinner)).check(matches(withSpinnerText(newCourse.getStatus())));


        //checks if views are GONE or not
        onView(withId(R.id.courseNameEditText)).check(matches(not(isDisplayed())));
        onView(withId(R.id.startDate)).check(matches(not(isDisplayed())));
        onView(withId(R.id.endDate)).check(matches(not(isDisplayed())));
        onView(withId(R.id.courseNameView)).check(matches(not(isDisplayed())));

    }

    public void testViewsExist() {

        onView(withId(R.id.courseNameEditText)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.statusNameView)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.statusSpinner)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.assessments)).check(matches(ViewMatchers.isDisplayed()));

    }

}
