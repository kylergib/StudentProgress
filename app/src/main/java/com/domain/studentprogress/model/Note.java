package com.domain.studentprogress.model;

import java.io.Serializable;

public class Note implements Serializable {
    private String message;
    private long id;
    private long courseID;
    public Note(String message, long id) {
        this.message = message;
        this.id = id;
    }
    public Note(String message, long id, long courseID) {
        this.message = message;
        this.id = id;
        this.courseID = courseID;
    }
    public Note(String message) {
        this.message = message;
    }
    public Note() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
