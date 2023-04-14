package com.kgibs87.studentprogress.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String id;
    private String term;

    public Course(String name, LocalDate startDate,
                  LocalDate endDate, String status) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Course(String name, ArrayList<String> notes) {
        this.name = name;
    }

    public String getCourseName() {
        return name;
    }

    public void setCourseName(String name) {
        this.name = name;
    }

    public LocalDate getCourseStartDate() {
        return startDate;
    }

    public void setCourseStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getCourseEndDate() {
        return endDate;
    }

    public void setCourseEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCourseStatus() {
        //TODO: logic for (in progress, completed, dropped, plan to take)
        return status;
    }

    public void setCourseStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
