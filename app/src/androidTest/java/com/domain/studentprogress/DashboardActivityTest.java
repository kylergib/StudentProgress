package com.domain.studentprogress;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.domain.studentprogress.controller.DashboardActivity;
import com.domain.studentprogress.controller.detail.TermDetailsActivity;
import com.domain.studentprogress.model.Assessment;
import com.domain.studentprogress.model.Course;
import com.domain.studentprogress.model.Instructor;
import com.domain.studentprogress.model.Note;
import com.domain.studentprogress.model.StudentDatabase;
import com.domain.studentprogress.model.Term;
import com.kgibs87.studentprogress.R;
import com.domain.studentprogress.controller.add.AddTermActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;


@RunWith(AndroidJUnit4.class)
public class DashboardActivityTest {

    @Rule
    public ActivityScenarioRule<DashboardActivity> activityRule = new ActivityScenarioRule<>(DashboardActivity.class);

    @Before
    public void setUp() {

        // Get a reference to the shared preferences file
        SharedPreferences sharedPreferences =
                InstrumentationRegistry.getInstrumentation().getTargetContext()
                        .getSharedPreferences("preferences", Context.MODE_PRIVATE);
        // Clear the shared preferences file
        sharedPreferences.edit().clear().apply();

        sharedPreferences.edit().putString("name", "Test").apply();
    }

    @Test
    public void testViewsExist() {
        onView(withId(R.id.welcomeText)).check(matches(ViewMatchers.isDisplayed()));

        //TODO: if recycler has data then statustext is not shown
//        onView(withId(R.id.termRecyclerView)).check(matches(ViewMatchers.isDisplayed())); //wont work if recycler view is blank
//        onView(withId(R.id.statusText)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.addTermButtonFragmentContainer)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void addTermClick() {
        Intents.init();
        onView(withTagValue(Matchers.is("addTermButton"))).perform(click());
        intended(hasComponent(TermDetailsActivity.class.getName())
        );
        onView(withId(R.id.courses)).check(matches(ViewMatchers.isDisplayed()));
//        onView(withId(R.id.courseRecyclerView)).check(matches(ViewMatchers.isDisplayed())); //wont work if recycler view is blank
        onView(withId(R.id.addCourseButton)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.startDate)).check(matches(ViewMatchers.isDisplayed()));

        //clicks cancel button to see if it goes back to previous activity correctly.
        onView(withTagValue(Matchers.is("cancelTermButton"))).perform(click());
        testViewsExist();
        Intents.release();
    }


    public void createFakeData(StudentDatabase mStudentDb) {


        Term newTerm = new Term("Term 1", LocalDate.of(2023,1,1),
                LocalDate.of(2023,6,30));
        Course newCourse = new Course("Test Course",LocalDate.of(2023,1,1),
                LocalDate.of(2023,1,2),"plan to take");
        Assessment newAssessment = new Assessment("Assessment Test", LocalDate.of(2023,1,1),
                LocalDate.of(2023,1,1),"performance");
        Note newNote = new Note("this is a message");
        Instructor newInstructor = new Instructor("Instructor name",
                "7652151070","testemail@gmail.com");
        newCourse.addCourseAssessment(newAssessment);
        newCourse.addCourseNote(newNote);
        newCourse.addCourseInstructor(newInstructor);

        newTerm.addTermCourse(newCourse);
        Log.d("AddTermTest course", String.valueOf(newTerm.getTermCourses()));
        for (Course course: newTerm.getTermCourses()) {
            Log.d("AddTermTest course", course.getCourseName());
        }

        //add term to database
        long termId = mStudentDb.addTerm(newTerm);
        Log.d("AddTermTest", String.valueOf(termId));
        newTerm.setId(termId);

        boolean courseListNotEmpty = !newTerm.getTermCourses().isEmpty();
        Log.d("AddTermTest course", String.valueOf(courseListNotEmpty));


        if (courseListNotEmpty) {
            newTerm.getTermCourses().forEach(course -> {
                boolean assessmentListNotEmpty = !course.getCourseAssessments().isEmpty();
                boolean instructorListNotEmpty = !course.getCourseInstructors().isEmpty();
                boolean noteListNotEmpty = !course.getCourseNotes().isEmpty();
                course.setTermID(termId);
                long courseId = mStudentDb.addCourse(course);

                if (courseId < 1) return;

                Log.d("Add Term:", "Added course with ID: " + courseId);
                course.setId(courseId);

                Log.d("Course:", course.getCourseName());
                if (assessmentListNotEmpty) {
                    course.getCourseAssessments().forEach(assessment -> {
                        assessment.setCourseID(courseId);
                        long assessmentId = mStudentDb.addAssessment(assessment);
                        Log.d("Add Term:", "Added assessment with ID: " + assessmentId);
                        assessment.setAssessmentId(assessmentId);

                    });
                } else {
                    Log.d("Add term", "Assessment List is empty");
                }
                if (instructorListNotEmpty) {
                    course.getCourseInstructors().forEach(instructor -> {
                        instructor.setCourseID(courseId);
                        long instructorId = mStudentDb.addInstructor(instructor);
                        Log.d("Add Term:", "Added instructor with ID: " + instructorId);
                        instructor.setId(instructorId);
                    });
                } else {
                    Log.d("Add term", "Instructor List is empty");
                }
                if (noteListNotEmpty) {
                    course.getCourseNotes().forEach(note -> {
                        note.setCourseID(courseId);
                        long noteID = mStudentDb.addNote(note);
                        Log.d("Add Term:", "Added note with ID: " + noteID);
                        note.setId(noteID);
                    });
                } else {
                    Log.d("Add term", "Note List is empty");
                }
            });
        } else {
            Log.d("Add term", "Course List is empty");
        }

    }



}