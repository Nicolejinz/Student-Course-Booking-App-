package com.example.booking.entity;

import java.io.Serializable;

public class Course implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String courseCode;
    private String courseName;
    private String studentId, instructorId;
    private String instructorName;
    private String day;
    private String studentEnrolled;
    private String startTime,endTime;

    public String getStudentEnrolled() {
        return studentEnrolled;
    }

    public void setStudentEnrolled(String studentEnrolled) {
        this.studentEnrolled = studentEnrolled;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    private String days,hours,description,capacity;
    private Accounts accounts=new Accounts();

    public Course() {
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public Course(String courseCode, String courseName, String instructorId, String days, String hours, String description, String capacity,String instructorName,String day,String studentEnrolled,String startTime,String endTime) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructorId = instructorId;
        this.days = days;
        this.hours = hours;
        this.description = description;
        this.capacity = capacity;
        this.instructorName=instructorName;
        this.day=day;
        this.studentEnrolled=studentEnrolled;
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public Course(String courseName, String courseCode, String studentId, String instructorId) {
        this.courseCode =courseCode;
        this.courseName = courseName;
        this.studentId = studentId;
        this.instructorId = instructorId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "CourseList{" +
                "courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", studentId='" + studentId + '\'' +
                ", instructorId='" + instructorId + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
