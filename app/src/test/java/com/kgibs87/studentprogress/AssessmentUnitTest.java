package com.kgibs87.studentprogress;

import static org.junit.Assert.assertTrue;
import com.kgibs87.studentprogress.model.Assessment;
import org.junit.Test;
import java.time.LocalDate;

public class AssessmentUnitTest {

    @Test
    public void objectiveAssessmentTest() {
        String assessmentTitle = "Test Assessment";
        String assessmentType = "Objective";
        LocalDate endDate = LocalDate.of(2023,01,2);
        Assessment testAssessment = new Assessment(assessmentType, assessmentTitle);
        testAssessment.setAssessmentEndDate(endDate);

        boolean titleMatch = testAssessment.getAssessmentTitle().equals(assessmentTitle);
        boolean typeMatch = testAssessment.getAssessmentType().equals(assessmentType);
        boolean dateMatch = testAssessment.getAssessmentEndDate().equals(endDate);
        assertTrue(titleMatch && typeMatch && dateMatch);
    }

    @Test
    public void performanceAssessmentTest() {
        String assessmentTitle = "Test Assessment";
        String assessmentType = "Performance";
        LocalDate endDate = LocalDate.of(2023,01,2);
        Assessment testAssessment = new Assessment(assessmentType, assessmentTitle);
        testAssessment.setAssessmentEndDate(endDate);

        boolean titleMatch = testAssessment.getAssessmentTitle().equals(assessmentTitle);
        boolean typeMatch = testAssessment.getAssessmentType().equals(assessmentType);
        boolean dateMatch = testAssessment.getAssessmentEndDate().equals(endDate);
        assertTrue(titleMatch && typeMatch && dateMatch);
    }
}
