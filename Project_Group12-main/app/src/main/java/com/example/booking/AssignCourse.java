package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class AssignCourse extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private String instructorId,instructorName,courseId,courseCode,courseName;
    private String studentEnrolled;
    private String startTime,endTime;
    private List<Course> courseLists=new LinkedList<>();
    private EditText instructorNameAssign,days,hours,description,capacity,day;
    private TextView instructorStartTime,instructorEndTime;
    private TextView saveInstructorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_course);


        Intent intent=getIntent();
        instructorId=intent.getStringExtra("InstructorId");
        instructorName=intent.getStringExtra("InstructorName");
        courseId=intent.getStringExtra("id");
        courseCode=intent.getStringExtra("courseCode");
        courseName=intent.getStringExtra("courseName");
        studentEnrolled=intent.getStringExtra("studentEnrolled");
        startTime=intent.getStringExtra("startTime");
        endTime=intent.getStringExtra("endTime");

        firebaseDatabase = FirebaseDatabase.getInstance();
        courseReference = firebaseDatabase.getReference("CourseList");

        instructorNameAssign=(EditText)findViewById(R.id.instructorName);
        days=(EditText)findViewById(R.id.days);
        hours=(EditText)findViewById(R.id.hours);
        description=(EditText)findViewById(R.id.description);
        capacity=(EditText)findViewById(R.id.capacity);
        saveInstructorId=(TextView)findViewById(R.id.instructorId);
        day=(EditText)findViewById(R.id.dayOfWeekText);
        instructorStartTime=(TextView)findViewById(R.id.instructorStartTime);
        instructorEndTime=(TextView)findViewById(R.id.instructorEndTime);



        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseLists.clear();
                for(DataSnapshot child:snapshot.getChildren()){
                    Course course=child.getValue(Course.class);
                    courseLists.add(course);

                }
                for(Course course:courseLists){
                    if(course.getCourseName().equals(courseName)){
                        instructorNameAssign.setText(course.getInstructorName());
                        days.setText(course.getDays());
                        hours.setText(course.getHours());
                        description.setText(course.getDescription());
                        capacity.setText(course.getCapacity());
                        day.setText(course.getDay());
                        instructorStartTime.setText(course.getStartTime());
                        instructorEndTime.setText(course.getEndTime());



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
    public boolean checkInputFormatValid(){
        String[] dayOfWeek=new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        for(Course course:courseLists){
            if(course.getCourseName().equals(courseName)){
                if(course.getInstructorId()!=null){
                    toastShow("This course has already been assigned.").show();
                    return false;
                }
                if(!instructorNameAssign.getText().toString().equals(instructorName)){
                    toastShow("Your name must the same with your account name!").show();
                    return false;
                }
                if(instructorId.equals(course.getInstructorId())) {
                    toastShow("You have already assigned this course.").show();
                    return false;
                }

                try {
                    Integer.parseInt(days.getText().toString());
                }catch (Exception e){
                    toastShow("Days should be a number!").show();
                    return false;
                }
                try {
                    Integer.parseInt(hours.getText().toString());
                }catch (Exception e){
                    toastShow("Hours should be a number!").show();
                    return false;
                }
                try{
                    Integer.parseInt(capacity.getText().toString());
                }catch (Exception e){
                    toastShow("Capacity should be a number!").show();
                    return false;
                }
                for(String s:dayOfWeek) {
                    if (day.getText().toString().equals("") || day.getText() == null||day.getText().toString().equalsIgnoreCase(s)) {
                        toastShow("You must enter day of week!");
                    }
                }
                return false;

            }
        }
        return true;
    }
    public boolean checkEdit(){
        for(Course course:courseLists){
            if(course.getCourseName().equals(courseName)){
                if(!instructorId.equals(course.getInstructorId())){
                    toastShow("You do not have the permission to edit the course.").show();
                    return false;
                }
            }
        }return true;
    }
    public void assignBtnMethod(View view) {
        Course course = new Course(courseCode, courseName, instructorId, days.getText().toString(), hours.getText().toString(),
                description.getText().toString(), capacity.getText().toString(),instructorNameAssign.getText().toString(),day.getText().toString(),studentEnrolled,startTime,endTime);
        if (checkInputFormatValid()) {
            courseReference.child(courseId).setValue(course);
            saveInstructorId.setText(instructorId);
            toastShow("Congratulation! You have successfully registered for this course ").show();
        }


    }
    public void editCourseInformation(View view){
        Course editCourse=new Course(courseCode,courseName,instructorId,days.getText().toString(),hours.getText().toString(),
                description.getText().toString(),capacity.getText().toString(),instructorNameAssign.getText().toString(),day.getText().toString(),studentEnrolled,startTime,endTime);
        if(checkEdit()) {
            courseReference.child(courseId).setValue(editCourse);
        }
    }
    public void unAssignCourse(View view){
        Course course=new Course(courseCode,courseName,null,"virtual","virtual","virtual","virtual","staff","virtual",studentEnrolled,startTime,endTime);
        courseReference.child(courseId).setValue(course);

    }
}

