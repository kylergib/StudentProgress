package com.kgibs87.studentprogress.model;

public class Instructor {
    private int id;
    private String name;
    private String number;
    private String email;
    private String course;

    public Instructor(int id, String name, String number, String email, String course) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.course = course;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
