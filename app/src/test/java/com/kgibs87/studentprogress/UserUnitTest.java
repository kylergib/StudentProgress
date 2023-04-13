package com.kgibs87.studentprogress;

import org.junit.Test;

import static org.junit.Assert.*;

import com.kgibs87.studentprogress.model.User;

public class UserUnitTest {

    @Test
    public void addUser() {

        String name = "userTest";
        User testUser = new User(name);
        boolean emptyTerms = testUser.getUserTerms().isEmpty();
        assertEquals(testUser.getUserName(), name);
        assertTrue(emptyTerms);
    }

}