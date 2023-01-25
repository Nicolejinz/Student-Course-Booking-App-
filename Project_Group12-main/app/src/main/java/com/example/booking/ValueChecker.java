package com.example.booking;

import com.example.booking.entity.Accounts;
import com.example.booking.entity.Course;
import com.example.booking.entity.Role;

import java.util.List;

public class ValueChecker {

    public ValueChecker() {
    }

    public int checkValueEmpty(String courseName, String courseCode, List<Course> courseLists) {
        if (courseName == null || courseName.equals("")) {
            return 1;
        }
        for (Course courseList : courseLists) {
            if (courseList.getCourseName().equals(courseName)) {
                return 2;
            }
        }
        if (courseCode == null || courseCode.equals("")) {
            return 3;
        }
        return 4;
    }

    public int checkValueValid(String name, String password, Accounts logInAccount) {
        if (name == null || name.equals("")) {
            return 1;
        }
        if (password == null || password.equals("")) {
            return 2;
        }
        if (logInAccount == null ) {
            return 3;
        }
        if (!logInAccount.getPassword().equals(password)) {
            return 4;
        }
        return 5;
    }

    public boolean findCourse(String courseNameEdit, String courseCodeEdit, Course courseList){
        if(courseList.getCourseName().equals(courseNameEdit) && courseList.getCourseCode().equals(courseCodeEdit)){
            return true;
        }
        return false;
    }




    public boolean CheckAdmin(String name){
        if (name.equals("admin")){
            return true;
        }
        return false;
    }

    public Role checkRole(Accounts logInAccount){
        if (logInAccount.getRole().equals(Role.INSTRUCTOR)){
            return Role.INSTRUCTOR;
        }
        else {
            return Role.STUDENT;
        }
    }
}
