package com.kgibs87.studentprogress.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class CourseTest {

    private Course course;

    @Before
    public void setUp() {
        course = DefaultObjectsForTests.defaultCourse();
    }

    @Test
    public void getCourseName() {
        assertEquals("C100 - Test",course.getCourseName());
    }

    @Test
    public void setCourseName() {
        String newName = "C102 - New";
        course.setCourseName(newName);
        assertEquals(newName, course.getCourseName());
    }

    @Test
    public void getCourseStartDate() {
        assertEquals(LocalDate.of(2023,1,2),course.getCourseStartDate());
    }

    @Test
    public void setCourseStartDate() {
        LocalDate newDate = LocalDate.of(2023,1,10);
        course.setCourseStartDate(newDate);
        assertEquals(newDate,course.getCourseStartDate());
    }

    @Test
    public void getCourseEndDate() {
        assertEquals(LocalDate.of(2023,2,2),course.getCourseEndDate());
    }

    @Test
    public void setCourseEndDate() {
        LocalDate newDate = LocalDate.of(2023,2,10);
        course.setCourseEndDate(newDate);
        assertEquals(newDate,course.getCourseEndDate());
    }

    @Test
    public void getCourseStatus() {
        assertEquals("plan to take", course.getCourseStatus());
    }

    @Test
    public void setCourseStatus() {
        String newStatus = "completed";
        course.setCourseStatus(newStatus);
        assertEquals(newStatus,course.getCourseStatus());
    }

    @Test
    public void getId() {
        assertEquals(1,course.getId());
    }

    @Test
    public void setId() {
        course.setId(2);
        assertEquals(2, course.getId());
    }

    @Test
    public void getTerm() {
//        assertEquals("Spring Term",course.getTerm());
    }

    @Test
    public void setTerm() {
        String newTerm = "Fall Term";
//        course.setTerm(newTerm);
//        assertEquals(newTerm,course.getTerm());
    }
}