package com.domain.studentprogress.model;

import java.io.Serializable;

public class Instructor implements Serializable {
    private long id;
    private String name;
    private String number;
    private String email;
    private long courseID;

    public Instructor(long id, String name, String number, String email) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
    }
    public Instructor(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }
    public Instructor() {

    }

    public String getInstructorName() {
        return name;
    }

    public void setInstructorName(String name) {
        this.name = name;
    }

    public String getInstructorPhoneNumber() {
        return number;
    }

    public void setInstructorPhoneNumber(String number) {
        this.number = number;
    }

    public String getInstructorEmail() {
        return email;
    }

    public void setInstructorEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

}
