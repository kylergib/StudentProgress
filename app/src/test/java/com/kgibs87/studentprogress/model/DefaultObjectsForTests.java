package com.kgibs87.studentprogress.model;

import com.kgibs87.studentprogress.model.Assessment;
import com.kgibs87.studentprogress.model.Course;
import com.kgibs87.studentprogress.model.Instructor;
import com.kgibs87.studentprogress.model.Note;
import com.kgibs87.studentprogress.model.Term;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DefaultObjectsForTests {

    public static Assessment defaultPerformanceAssessment() {
        String assessmentTitle = "Performance Assessment";
        String assessmentType = "Performance";
        LocalDate endDate = LocalDate.of(2023,1,2);
        Assessment testAssessment = new Assessment(1, assessmentType, assessmentTitle, endDate);
        testAssessment.setAssessmentEndDate(endDate);

        return testAssessment;
    }

    public static Assessment defaultObjectiveAssessment() {
        String assessmentTitle = "Objective Assessment";
        String assessmentType = "Objective";
        LocalDate endDate = LocalDate.of(2023,1,2);
        Assessment testAssessment = new Assessment(2, assessmentType, assessmentTitle, endDate);
        testAssessment.setAssessmentEndDate(endDate);

        return testAssessment;
    }

    public static Instructor defaultInstructor() {
        String name = "Test Instructor";
        String number = "5555555050";
        String email = "testemail@gmail.com";

        return new Instructor(1, name, number, email);
    }

    public static Course defaultCourse() {
        String courseName = "C100 - Test";
        LocalDate courseStartDate = LocalDate.of(2023,1,2);
        LocalDate courseEndDate = LocalDate.of(2023,2,2);
        String courseStatus = "plan to take";

        return new Course(courseName,courseStartDate,
                courseEndDate,courseStatus,
                1,1);
    }
    public static Note defaultNote() {
        return new Note("This is a test note",1);
    }

    public static Term defaultTerm() {
        return new Term("Spring Term", LocalDate.of(2023,1,1),LocalDate.of(2023,6,30),1);
    }
}
