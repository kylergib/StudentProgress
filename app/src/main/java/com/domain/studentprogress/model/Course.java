package com.domain.studentprogress.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private long id;
    private long termID;
    private List<Assessment> courseAssessments;
    private List<Instructor> courseInstructors;
    private List<Note> courseNotes;

    public Course(String name, LocalDate startDate,
                  LocalDate endDate, String status,
                  long id, long termID) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        setCourseStatus(status);
        this.id = id;
        this.termID = termID;
        courseAssessments = new ArrayList<>();
        courseInstructors = new ArrayList<>();
        courseNotes = new ArrayList<>();
    }
    public Course(String name, LocalDate startDate,
                  LocalDate endDate, String status) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        setCourseStatus(status);
        courseAssessments = new ArrayList<>();
        courseInstructors = new ArrayList<>();
        courseNotes = new ArrayList<>();
    }
    public Course() {
        courseAssessments = new ArrayList<>();
        courseInstructors = new ArrayList<>();
        courseNotes = new ArrayList<>();
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
        return status;
    }

    public void setCourseStatus(String status) {
        boolean isInProgress = status.equalsIgnoreCase("in progress");
        boolean isCompleted = status.equalsIgnoreCase("completed");
        boolean isDropped = status.equalsIgnoreCase("dropped");
        boolean isPlanToTake = status.equalsIgnoreCase("plan to take");

        if (isInProgress || isCompleted || isDropped || isPlanToTake)
            this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTermID() {
        return termID;
    }

    public void setTermID(long term) {
        this.termID = term;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Assessment> getCourseAssessments() {
        return courseAssessments;
    }
    public void addCourseAssessment(Assessment courseAssessment) {
        this.courseAssessments.add(courseAssessment);
    }

    public void setCourseAssessments(List<Assessment> courseAssessments) {
        this.courseAssessments = courseAssessments;
    }

    public List<Instructor> getCourseInstructors() {
        return courseInstructors;
    }
    public void addCourseInstructor(Instructor courseInstructor) {
        this.courseInstructors.add(courseInstructor);
    }

    public void setCourseInstructors(List<Instructor> courseInstructors) {
        this.courseInstructors = courseInstructors;
    }

    public List<Note> getCourseNotes() {
        return courseNotes;
    }
    public void addCourseNote(Note courseNote) {
        this.courseNotes.add(courseNote);
    }

    public void setCourseNotes(List<Note> courseNotes) {
        this.courseNotes = courseNotes;
    }
}
