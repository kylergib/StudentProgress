package com.kgibs87.studentprogress.model;

import java.time.LocalDate;
import java.util.Date;

public class Term {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int id;

    public Term(String name, LocalDate startDate, LocalDate endDate, int id) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
