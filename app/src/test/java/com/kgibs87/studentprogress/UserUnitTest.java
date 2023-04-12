package com.kgibs87.studentprogress;

import org.junit.Test;

import static org.junit.Assert.*;

import com.kgibs87.studentprogress.model.User;

public class UserUnitTest {
    private String name = "userTest";
    private User testUser;
    @Test
    public void addUser() {
//        String name = "addUserTest";
        testUser = new User(name);
        boolean nameMatch = testUser.getUserName().equals(name);
        boolean emptyTerms = testUser.getUserTerms().isEmpty();
        assertTrue(nameMatch && emptyTerms);
    }
//    @Test
//    public void addTerm() {
//        String name = "addTermTest";
//        User testUser = new User(name);
//
//    }
}