package com.kgibs87.studentprogress;

import static org.junit.Assert.assertTrue;

import com.kgibs87.studentprogress.model.Instructor;

import org.junit.Test;

public class InstructorUnitTest {


    @Test
    public void addInstructor() {
        String name = "Test Instructor";
        String number = "555-555-5050";
        String email = "testemail@gmail.com";

        Instructor newInstructor = defaultInstructor();

        checkInstructorProperties(newInstructor, name, number, email);
    }

    @Test
    public void changeInstructorProperties() {
        String name = "New Instructor";
        String number = "655-555-5050";
        String email = "newemail@gmail.com";

        Instructor newInstructor = defaultInstructor();

        newInstructor.setInstructorName(name);
        newInstructor.setInstructorPhoneNumber(number);
        newInstructor.setInstructorEmail(email);

        checkInstructorProperties(newInstructor, name, number, email);


    }

    public void checkInstructorProperties(Instructor instructor, String name, String number, String email) {
        boolean nameMatch = instructor.getInstructorName().equals(name);
        boolean numberMatch = instructor.getInstructorPhoneNumber().equals(number);
        boolean emailMatch = instructor.getInstructorEmail().equals(email);

        assertTrue(nameMatch && numberMatch && emailMatch);
    }

    public Instructor defaultInstructor() {
        String name = "Test Instructor";
        String number = "555-555-5050";
        String email = "testemail@gmail.com";


        return new Instructor(name, number, email);
    }


}
