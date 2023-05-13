package com.domain.studentprogress.model;

import java.time.LocalDate;


public class Assessment {
    private String type;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private long id;
    private long courseID;

    public Assessment() {
    }
    public Assessment(String type, String title) {
        setAssessmentType(type);
        setAssessmentTitle(title);
    }

    public Assessment(long id, String type, String title, LocalDate endDate) {
        setAssessmentType(type);
        this.title = title;
        this.endDate = endDate;
        this.id = id;
    }
    public Assessment(String title, LocalDate startDate, LocalDate endDate,String type) {
        setAssessmentType(type);
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getAssessmentType() {
        return type;
    }

    public void setAssessmentType(String type) {
        boolean isPerformance = type.equalsIgnoreCase("performance");
        boolean isObjective = type.equalsIgnoreCase("objective");
        if (isPerformance || isObjective) this.type = type;

    }

    public String getAssessmentTitle() {
        return title;
    }

    public void setAssessmentTitle(String title) {
        this.title = title;
    }

    public LocalDate getAssessmentStartDate() {
        return startDate;
    }

    public void setAssessmentStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getAssessmentEndDate() {
        return endDate;
    }

    public void setAssessmentEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getAssessmentId() {
        return id;
    }

    public void setAssessmentId(long id) {
        this.id = id;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

}
