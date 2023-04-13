package com.kgibs87.studentprogress;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.kgibs87.studentprogress.model.Assessment;
import com.kgibs87.studentprogress.model.Course;
import com.kgibs87.studentprogress.model.Instructor;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseUnitTest {

    @Test
    public void createNewCourseTest() {
        String courseName = "C100 - Test";
        LocalDate courseStartDate = LocalDate.of(2023,1,2);
        LocalDate courseEndDate = LocalDate.of(2023,2,2);
        String courseStatus = "plan to take";


        Course course = DefaultObjectsForTests.defaultCourse();

        //check all default matches the correct info when created
        assertEquals(course.getCourseName(), courseName);


    }


}
