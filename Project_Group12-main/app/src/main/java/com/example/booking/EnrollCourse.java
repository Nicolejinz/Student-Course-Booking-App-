package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.entity.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class EnrollCourse extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private List<Course> courses=new LinkedList<>();
    private List<String> studentList=new LinkedList<>();
    private List<String> checkStudentList=new LinkedList<>();
    private String studentName,courseId;
    private String enrolledStudent;
    private Course courseEnroll;
    private TextView startTime, endTime;
    private String checkStartTime,checkEndTime;
    private List<Course> enrolledCourse=new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_course);
        firebaseDatabase=FirebaseDatabase.getInstance();
        courseReference=firebaseDatabase.getReference("CourseList");
        Intent intent=getIntent();
        courseEnroll=(Course) intent.getSerializableExtra("course");
        courseId=intent.getStringExtra("courseId");
        studentName=intent.getStringExtra("studentName");
        startTime=(TextView)findViewById(R.id.startTime);
        endTime=(TextView)findViewById(R.id.endTime);

        startTime.setText(courseEnroll.getStartTime());
        endTime.setText(courseEnroll.getEndTime());

        checkStartTime=startTime.getText().toString();
        checkEndTime=endTime.getText().toString();



        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Course course=child.getValue(Course.class);
                    course.setId(child.getKey());
                    courses.add(course);
                    if(course.getId().equals(courseId) && course.getStudentEnrolled()!=null) {
                        String[] string=course.getStudentEnrolled().split(",");
                        for(int i=0;i<string.length;i++){
                            studentList.add(string[i]);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private Toast toastShow(String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        return toast;
    }
    public boolean checkEnroll(){
        for(Course course:courses){
            if(course.getId().equals(courseId)&&course.getStudentEnrolled()!=null){
                String[] strings=course.getStudentEnrolled().split(",");
                checkStudentList.clear();
                for(int i=0;i<strings.length;i++) {
                    checkStudentList.add(strings[i]);
                    if (checkStudentList.contains(studentName)) {
                        toastShow("You have already enrolled this course!").show();
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public boolean checkTime() {
        for (Course course : courses) {
            if (course.getStudentEnrolled() != null) {
                String[] strings = course.getStudentEnrolled().split(",");
                for (int i = 0; i < strings.length; i++) {
                    checkStudentList.add(strings[i]);
                    if (checkStudentList.contains(studentName)) {
                        enrolledCourse.add(course);
                    }
                }

            }

        }
        System.out.println(enrolledCourse);
        for(Course course:enrolledCourse){
            if(!(Double.parseDouble(course.getStartTime())<=Double.parseDouble(checkStartTime) && Double.parseDouble(course.getEndTime())<=Double.parseDouble(checkStartTime))
            || (Double.parseDouble(course.getStartTime())>=Double.parseDouble(checkEndTime)&&Double.parseDouble(course.getEndTime())>=Double.parseDouble(checkEndTime))){
                toastShow("You have the time conflict with some courses").show();
                return false;
            }

        }
        return true;
    }

    public void enrollMethod(View view){
        if(checkEnroll()) {
            if (checkTime()) {
                enrolledStudent = "";
                studentList.add(studentName);
                for (int i = 0; i < studentList.size(); i++) {
                    enrolledStudent = enrolledStudent + "," + studentList.get(i);
                }
                courseEnroll.setStudentEnrolled(enrolledStudent);
                courseReference.child(courseId).setValue(courseEnroll);
            }
        }
        toastShow("Congratulations! You enroll in this course successfully!").show();
        finish();
    }
    public void unEnrollMethod(View view){
        enrolledStudent="";
        if (studentList.contains(studentName)) {
            studentList.remove(studentName);
            for(int i=0;i<studentList.size();i++){
                enrolledStudent=enrolledStudent + "," + studentList.get(i);
            }
            courseEnroll.setStudentEnrolled(enrolledStudent);
            courseReference.child(courseId).setValue(courseEnroll);
        }else {
            toastShow("You don't have permissions to UNENROLL it! Please enroll it first!").show();
        }
        toastShow("You unenroll this course successfully!").show();
    }
}