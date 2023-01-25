package com.example.booking;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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

public class CreateCourse extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private List<Course> courses = new LinkedList<>();
    private String courseName, courseCode, instructorId, days, hours, description, capacity, instructorName,day,studentEnrolled,startTime,endTime;
    private EditText editCourseName, editCourseCode,courseStartTime,courseEndTime;
    private ValueChecker valueChecker = new ValueChecker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        firebaseDatabase = FirebaseDatabase.getInstance();
        courseReference = firebaseDatabase.getReference("CourseList");
        editCourseCode = (EditText) findViewById(R.id.editCourseCode);
        editCourseName = (EditText) findViewById(R.id.editCourseName);
        courseStartTime=(EditText)findViewById(R.id.courseStartTime);
        courseEndTime=(EditText)findViewById(R.id.courseEndTime);

        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Course course = child.getValue(Course.class);
                    courses.add(course);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        editCourseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                courseName = editable.toString();
            }
        });
        editCourseCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                courseCode = editable.toString();
            }
        });

    }

    private Toast toastShow(String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        return toast;
    }

    public boolean checkValueEmpty() {
        if (courseName == null || courseName.equals("")) {
            toastShow("Course name cannot be empty.").show();
            return false;
        }
        if(courseStartTime==null||courseStartTime.equals("")){
            toastShow("Start Time cannot be empty.").show();
            return false;
        }
        if (courseEndTime == null || courseEndTime.equals("")) {
            toastShow("End time cannot be empty.").show();
            return false;
        }
        try{
            Double.parseDouble(courseStartTime.getText().toString());
        }catch (Exception e){
            return false;
        }
        try{
            Double.parseDouble(courseEndTime.getText().toString());
        }
        catch (Exception e){
            return false;
        }
        for (Course course : courses) {
            if (course.getCourseName().equals(courseName)) {
                toastShow("This course name is exist, please change a name.").show();
                return false;
            }
        }
        if (courseCode == null || courseCode.equals("")) {
            toastShow("Course code cannot be empty.").show();
            return false;

        }
        return true;
    }

    public void createBtn(View view) {
        int check = valueChecker.checkValueEmpty(courseName, courseCode, courses);
        if (check == 4) {
            instructorName = "staff";
            days = "virtual";
            hours = "virtual";
            description = "virtual";
            capacity = "virtual";
            day="virtual";
            startTime=courseStartTime.getText().toString();
            endTime=courseEndTime.getText().toString();
            Course course = new Course(courseCode, courseName, instructorId, days, hours, description, capacity, instructorName,day,studentEnrolled,startTime,endTime);
            courseReference.push().setValue(course);
            finish();
        }
        if (check == 3) {
            toastShow("Course code cannot be empty.").show();
        }
        if (check == 2) {
            toastShow("This course name is exist, please change a name.").show();
        }
        if (check == 1) {
            toastShow("Course name cannot be empty.").show();
        }
    }
//        if (checkValueEmpty()) {
//            instructorName="staff";
//            days="virtual";
//            hours="virtual";
//            description="virtual";
//            capacity="virtual";
//            Course course = new Course(courseCode, courseName,instructorId,days,hours,description,capacity,instructorName);
//            courseReference.push().setValue(course);
//            finish();
//
//        }


}