package com.kgibs87.studentprogress.model;

public class Note {
    private String message;
    private String course;
    private int id;

    public Note(String message, String course, int id) {
        this.message = message;
        this.course = course;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
