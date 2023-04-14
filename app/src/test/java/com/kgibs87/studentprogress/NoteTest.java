package com.kgibs87.studentprogress;

import static org.junit.Assert.*;

import com.kgibs87.studentprogress.model.Note;

import org.junit.Before;
import org.junit.Test;

public class NoteTest {

    private Note defaultNote;

    @Before
    public void defaultObjectCreate() {
        defaultNote = DefaultObjectsForTests.defaultNote();
    }

    @Test
    public void getMessage() {
        assertEquals("This is a test note", defaultNote.getMessage());
    }

    @Test
    public void setMessage() {
        String newMessage = "This is a new message";
        defaultNote.setMessage(newMessage);
        assertEquals(newMessage, defaultNote.getMessage());
    }

    @Test
    public void getCourse() {
        assertEquals(DefaultObjectsForTests.defaultCourse().getCourseName(), defaultNote.getCourse());
    }

    @Test
    public void setCourse() {
        String newCourseName = "C100 - New";
        defaultNote.setCourse(newCourseName);

        assertEquals(newCourseName, defaultNote.getCourse());
    }

    @Test
    public void getId() {
        assertEquals(1, defaultNote.getId());
    }

    @Test
    public void setId() {
        defaultNote.setId(2);
        assertEquals(2, defaultNote.getId());
    }
}
