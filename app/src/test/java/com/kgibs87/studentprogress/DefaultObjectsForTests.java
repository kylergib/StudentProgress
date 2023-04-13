package com.kgibs87.studentprogress;

import com.kgibs87.studentprogress.model.Assessment;
import com.kgibs87.studentprogress.model.Instructor;

import java.time.LocalDate;

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

//    public static Course defaultCourse() {
//
//    }
}
