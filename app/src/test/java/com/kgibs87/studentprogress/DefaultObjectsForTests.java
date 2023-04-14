package com.kgibs87.studentprogress;

import com.kgibs87.studentprogress.model.Assessment;
import com.kgibs87.studentprogress.model.Course;
import com.kgibs87.studentprogress.model.Instructor;
import com.kgibs87.studentprogress.model.Note;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DefaultObjectsForTests {

    public static Assessment defaultPerformanceAssessment() {
        String assessmentTitle = "Performance Assessment";
        String assessmentType = "Performance";
        LocalDate endDate = LocalDate.of(2023,1,2);
        Assessment testAssessment = new Assessment(1, assessmentType, assessmentTitle, endDate, "Test Course");
        testAssessment.setAssessmentEndDate(endDate);

        return testAssessment;
    }

    public static Assessment defaultObjectiveAssessment() {
        String assessmentTitle = "Objective Assessment";
        String assessmentType = "Objective";
        LocalDate endDate = LocalDate.of(2023,1,2);
        Assessment testAssessment = new Assessment(2, assessmentType, assessmentTitle, endDate, "Test Course");
        testAssessment.setAssessmentEndDate(endDate);

        return testAssessment;
    }

    public static Instructor defaultInstructor() {
        String name = "Test Instructor";
        String number = "5555555050";
        String email = "testemail@gmail.com";

        return new Instructor(1, name, number, email, defaultCourse().getCourseName());
    }

    public static Course defaultCourse() {
        String courseName = "C100 - Test";
        LocalDate courseStartDate = LocalDate.of(2023,1,2);
        LocalDate courseEndDate = LocalDate.of(2023,2,2);
        String courseStatus = "plan to take";

        return new Course(courseName,courseStartDate,
                courseEndDate,courseStatus,
                1,"Spring Term");
    }
    public static Note defaultNote() {
        return new Note("This is a test note", defaultCourse().getCourseName(),1);
    }
}
