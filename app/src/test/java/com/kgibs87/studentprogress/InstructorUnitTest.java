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

        Instructor newInstructor = new Instructor(name, number, email);

        boolean nameMatch = newInstructor.getInstructorName().equals(name);
        boolean numberMatch = newInstructor.getInstructorPhoneNumber().equals(number);
        boolean emailMatch = newInstructor.getInstructorEmail().equals(email);

        assertTrue(nameMatch && numberMatch && emailMatch);

    }
}
