package com.kgibs87.studentprogress;

import com.kgibs87.studentprogress.model.Assessment;
import com.kgibs87.studentprogress.model.Course;
import com.kgibs87.studentprogress.model.Instructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DefaultObjectsForTests {

    public static Assessment defaultPerformanceAssessment() {
        String assessmentTitle = "Test Assessment";
        String assessmentType = "Performance";
        LocalDate endDate = LocalDate.of(2023,1,2);
        Assessment testAssessment = new Assessment(assessmentType, assessmentTitle);
        testAssessment.setAssessmentEndDate(endDate);

        return testAssessment;
    }

    public static Assessment defaultObjectiveAssessment() {
        String assessmentTitle = "Test Assessment";
        String assessmentType = "Objective";
        LocalDate endDate = LocalDate.of(2023,1,2);
        Assessment testAssessment = new Assessment(assessmentType, assessmentTitle);
        testAssessment.setAssessmentEndDate(endDate);

        return testAssessment;
    }

    public static Instructor defaultInstructor() {
        String name = "Test Instructor";
        String number = "555-555-5050";
        String email = "testemail@gmail.com";

        return new Instructor(name, number, email);
    }

    public static Course defaultCourse() {
        String courseName = "C100 - Test";
        ArrayList<Assessment> courseAssessments = new ArrayList<>();
        ArrayList<String> courseNotes = new ArrayList<>();
        courseNotes.add("Test Note 1");
        LocalDate courseStartDate = LocalDate.of(2023,1,2);
        LocalDate courseEndDate = LocalDate.of(2023,2,2);
        ArrayList<Instructor> courseInstructors = new ArrayList<>();
        String courseStatus = "plan to take";

        courseAssessments.add(defaultPerformanceAssessment());
        courseInstructors.add(defaultInstructor());

        return new Course(courseName,courseAssessments,courseNotes,courseStartDate,
                courseEndDate,courseInstructors,courseStatus);
    }
}
