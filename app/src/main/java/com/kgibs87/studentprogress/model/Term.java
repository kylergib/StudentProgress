package com.kgibs87.studentprogress.model;

import java.util.Date;

public class Term {
    private String name;
    private Date startDate;
    private Date endDate;
    private int id;

    public Term(String name, Date startDate, Date endDate, int id) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
