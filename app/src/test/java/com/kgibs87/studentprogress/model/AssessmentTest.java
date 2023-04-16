package com.kgibs87.studentprogress.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class AssessmentTest {

    private Assessment objectiveAssessment;
    private Assessment performanceAssessment;

    @Before
    public void setDefaultAssessment() {
        performanceAssessment = DefaultObjectsForTests.defaultPerformanceAssessment();
    }

    @Test
    public void getAssessmentType() {
        objectiveAssessment = DefaultObjectsForTests.defaultObjectiveAssessment();
        assertEquals("Performance",performanceAssessment.getAssessmentType());
        assertEquals("Objective",objectiveAssessment.getAssessmentType());
    }

    @Test
    public void setAssessmentType() {
        objectiveAssessment = DefaultObjectsForTests.defaultObjectiveAssessment();
        performanceAssessment.setAssessmentType("Objective");
        objectiveAssessment.setAssessmentType("Performance");

        assertEquals("Objective",performanceAssessment.getAssessmentType());
        assertEquals("Performance",objectiveAssessment.getAssessmentType());
    }

    @Test
    public void getAssessmentTitle() {
        assertEquals("Performance Assessment",performanceAssessment.getAssessmentTitle());
    }

    @Test
    public void setAssessmentTitle() {
        String newTitle = "New Title Assessment";
        performanceAssessment.setAssessmentTitle(newTitle);
        assertEquals(newTitle,performanceAssessment.getAssessmentTitle());
    }

    @Test
    public void getAssessmentEndDate() {
        assertEquals(LocalDate.of(2023,1,2),performanceAssessment.getAssessmentEndDate());
    }

    @Test
    public void setAssessmentEndDate() {
        LocalDate newDate = LocalDate.of(2023, 2, 3);
        performanceAssessment.setAssessmentEndDate(newDate);
        assertEquals(newDate,performanceAssessment.getAssessmentEndDate());

    }

    @Test
    public void getAssessmentId() {
        assertEquals(1, performanceAssessment.getAssessmentId());
    }

    @Test
    public void setAssessmentId() {
        performanceAssessment.setAssessmentId(3);
        assertEquals(3, performanceAssessment.getAssessmentId());
    }

    @Test
    public void getCourse() {
        assertEquals("Test Course",performanceAssessment.getCourse());
    }

    @Test
    public void setCourse() {
        String newCourse = "New Course";
        performanceAssessment.setCourse(newCourse);
        assertEquals(newCourse,performanceAssessment.getCourse());
    }
}