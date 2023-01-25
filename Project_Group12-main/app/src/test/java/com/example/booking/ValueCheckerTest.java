package com.example.booking;

import static org.junit.Assert.assertEquals;

import com.example.booking.entity.Accounts;
import com.example.booking.entity.Course;
import com.example.booking.entity.Role;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;


public class ValueCheckerTest {
    ValueChecker valueChecker = new ValueChecker();



    @Test
    public void checkValueEmpty() {
        List<Course> courseLists = new LinkedList<>();
        Course courseList = new Course();
        for (int i = 0; i<40; i++){
            courseList.setCourseName("idk");
            courseList.setCourseCode("idc");
            courseLists.add(courseList);
        }
        int actual = valueChecker.checkValueEmpty("idk","idc",courseLists);
        int except =2;
        assertEquals(except,actual);

    }

    @Test
    public void checkValueValid() {
        List<Accounts> accounts = new LinkedList<>();
        Accounts logInAccount = new Accounts();
        logInAccount.setName("idk");
        logInAccount.setPassword("idc");
        int actual = valueChecker.checkValueValid("idk","123", logInAccount);
        int except =4;
        assertEquals(except,actual);
    }
    @Test
    public void CheckAdmin(){
        boolean actual = valueChecker.CheckAdmin("admin");
        boolean except = true;
        assertEquals(except,actual);
    }

    @Test
    public void checkRole(){
        Accounts logInAccount = new Accounts();
        logInAccount.setRole(Role.INSTRUCTOR);
        Role actual = valueChecker.checkRole(logInAccount);
        Role except = Role.INSTRUCTOR;
        assertEquals(except, actual);
    }

    @Test
    public void findCourse(){
        Course courseList = new Course();
        courseList.setCourseName("idk");
        courseList.setCourseCode("idc");

        boolean actual = valueChecker.findCourse("idk","idc", courseList);
        boolean except = true;
        assertEquals(except, actual);
    }
}