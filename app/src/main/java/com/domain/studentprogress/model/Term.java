package com.domain.studentprogress.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Term implements Serializable {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private long id;
    private List<Course> termCourses;

    public Term(String name, LocalDate startDate, LocalDate endDate, long id) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
        this.termCourses = new ArrayList<>();
    }
    public Term(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termCourses = new ArrayList<>();
    }
    public Term() {
        this.termCourses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Course> getTermCourses() {
        return termCourses;
    }

    public void addTermCourse(Course termCourse) {
        this.termCourses.add(termCourse);
    }
    public void setTermCourses(List<Course> termCourses) {
        this.termCourses = termCourses;
    }
}
