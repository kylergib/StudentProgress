package com.kgibs87.studentprogress.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InstructorTest {
    //tODO: redo all tests

    private Instructor instructor;

    @Before
    public void setUp() {
        instructor = DefaultObjectsForTests.defaultInstructor();
    }

    @Test
    public void getInstructorName() {
        assertEquals("Test Instructor",instructor.getInstructorName());
    }

    @Test
    public void setInstructorName() {
        String newName = "New Instructor";
        instructor.setInstructorName(newName);
        assertEquals(newName,instructor.getInstructorName());
    }

    @Test
    public void getInstructorPhoneNumber() {
        assertEquals("5555555050",instructor.getInstructorPhoneNumber());
    }

    @Test
    public void setInstructorPhoneNumber() {
        String newNumber = "1234567890";
        instructor.setInstructorPhoneNumber(newNumber);
        assertEquals(newNumber,instructor.getInstructorPhoneNumber());
    }

    @Test
    public void getInstructorEmail() {
        assertEquals("testemail@gmail.com",instructor.getInstructorEmail());
    }

    @Test
    public void setInstructorEmail() {
        String newEmail = "newemail@gmail.com";
        instructor.setInstructorEmail(newEmail);
        assertEquals(newEmail,instructor.getInstructorEmail());
    }

    @Test
    public void getId() {
        assertEquals(1,instructor.getId());
    }

    @Test
    public void setId() {
        instructor.setId(2);
        assertEquals(2,instructor.getId());
    }

    @Test
    public void getCourse() {
        assertEquals(DefaultObjectsForTests.defaultCourse().getCourseName(),instructor.getCourseID());
    }

    @Test
    public void setCourse() {
//        String newCourse = "New course";
//        instructor.setCourse(newCourse);
//        assertEquals(newCourse,instructor.getCourse());
    }
}