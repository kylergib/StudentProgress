package com.kgibs87.studentprogress.model;

import java.time.LocalDate;


public class Assessment {
    private String assessmentType;
    private String assessmentTitle;
    private LocalDate assessmentEndDate;

    public Assessment(String assessmentType, String assessmentTitle) {
        setAssessmentType(assessmentType);
        setAssessmentTitle(assessmentTitle);
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        //TODO: move logic to activity?
        boolean isPerformance = assessmentType.equalsIgnoreCase("performance");
        boolean isObjective = assessmentType.equalsIgnoreCase("objective");
        if (isPerformance || isObjective) this.assessmentType = assessmentType;

    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public LocalDate getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(LocalDate assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }
}
