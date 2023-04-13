package com.kgibs87.studentprogress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.kgibs87.studentprogress.model.Assessment;
import org.junit.Test;
import java.time.LocalDate;

public class AssessmentUnitTest {

    @Test
    public void objectiveAssessmentTest() {

        String assessmentTitle = "Test Assessment";
        String assessmentType = "Objective";
        LocalDate endDate = LocalDate.of(2023,1,2);

        Assessment testAssessment = DefaultObjectsForTests.defaultObjectiveAssessment();

        checkMatches(testAssessment,assessmentTitle,assessmentType,endDate);
    }

    @Test
    public void changeObjectiveAssessmentTest() {

        String assessmentTitle = "New Assessment";
        String assessmentType = "Performance";
        LocalDate endDate = LocalDate.of(2023,1,3);

        Assessment testAssessment = DefaultObjectsForTests.defaultObjectiveAssessment();

        testAssessment.setAssessmentTitle(assessmentTitle);
        testAssessment.setAssessmentType(assessmentType);
        testAssessment.setAssessmentEndDate(endDate);

        checkMatches(testAssessment,assessmentTitle,assessmentType,endDate);

    }

    @Test
    public void performanceAssessmentTest() {

        String assessmentTitle = "Test Assessment";
        String assessmentType = "Performance";
        LocalDate endDate = LocalDate.of(2023,1,2);


        Assessment testAssessment = DefaultObjectsForTests.defaultPerformanceAssessment();

        checkMatches(testAssessment,assessmentTitle,assessmentType,endDate);
    }

    @Test
    public void changePerformanceAssessmentTest() {

        String assessmentTitle = "New Assessment";
        String assessmentType = "Objective";
        LocalDate endDate = LocalDate.of(2023,1,3);

        Assessment testAssessment = DefaultObjectsForTests.defaultPerformanceAssessment();

        testAssessment.setAssessmentTitle(assessmentTitle);
        testAssessment.setAssessmentType(assessmentType);
        testAssessment.setAssessmentEndDate(endDate);

        checkMatches(testAssessment,assessmentTitle,assessmentType,endDate);

    }

    public void checkMatches(Assessment testAssessment, String assessmentTitle,
                             String assessmentType, LocalDate endDate) {
        //TODO: change this to assertEquals so it shows comparison in log if test fails.
        boolean titleMatch = testAssessment.getAssessmentTitle().equals(assessmentTitle);
        boolean typeMatch = testAssessment.getAssessmentType().equals(assessmentType);
        boolean dateMatch = testAssessment.getAssessmentEndDate().equals(endDate);

        assertEquals(testAssessment.getAssessmentTitle(), assessmentTitle);
        assertEquals(testAssessment.getAssessmentType(), assessmentType);
        assertTrue(titleMatch && typeMatch && dateMatch);
    }
}