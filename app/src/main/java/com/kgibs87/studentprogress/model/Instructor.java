package com.kgibs87.studentprogress.model;

public class Instructor {
    private String instructorName;
    private String instructorPhoneNumber;
    private String instructorEmail;

    public Instructor(String instructorName, String instructorPhoneNumber, String instructorEmail) {
        this.instructorName = instructorName;
        this.instructorPhoneNumber = instructorPhoneNumber;
        this.instructorEmail = instructorEmail;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhoneNumber() {
        return instructorPhoneNumber;
    }

    public void setInstructorPhoneNumber(String instructorPhoneNumber) {
        this.instructorPhoneNumber = instructorPhoneNumber;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }


}
