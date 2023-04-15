package com.kgibs87.studentprogress.model;

import java.time.LocalDate;


public class Assessment {
    private String type;
    private String title;
    private LocalDate endDate;
    private int id;
    private String course;

    public Assessment(String type, String title) {
        setAssessmentType(type);
        setAssessmentTitle(title);
    }

    public Assessment(int id, String type, String title, LocalDate endDate,  String course) {
        setAssessmentType(type);
        this.title = title;
        this.endDate = endDate;
        this.id = id;
        this.course = course;
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

    public LocalDate getAssessmentEndDate() {
        return endDate;
    }

    public void setAssessmentEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getAssessmentId() {
        return id;
    }

    public void setAssessmentId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
