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
    private int id;
    private String term;

    public Course(String name, LocalDate startDate,
                  LocalDate endDate, String status,
                  int id, String term) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.id = id;
        this.term = term;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
