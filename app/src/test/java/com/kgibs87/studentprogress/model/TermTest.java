package com.kgibs87.studentprogress.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class TermTest {

    private Term term;

    @Before
    public void setUp() {
        term = DefaultObjectsForTests.defaultTerm();
    }

    @Test
    public void getName() {
        assertEquals("Spring Term",term.getName());
    }

    @Test
    public void setName() {
        String newName = "Fall Term";
        term.setName(newName);
        assertEquals(newName, term.getName());
    }

    @Test
    public void getStartDate() {
        assertEquals(LocalDate.of(2023,1,1),term.getStartDate());
    }

    @Test
    public void setStartDate() {
        LocalDate newStart = LocalDate.of(2023,2,1);
        term.setStartDate(newStart);
        assertEquals(newStart,term.getStartDate());
    }

    @Test
    public void getEndDate() {
        assertEquals(LocalDate.of(2023,6,30),term.getEndDate());
    }

    @Test
    public void setEndDate() {
        LocalDate newEnd = LocalDate.of(2023,7,31);
        term.setEndDate(newEnd);
        assertEquals(newEnd,term.getEndDate());
    }

    @Test
    public void getId() {
        assertEquals(1,term.getId());
    }

    @Test
    public void setId() {
        term.setId(2);
        assertEquals(2,term.getId());
    }
}