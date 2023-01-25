package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class CourseEdit extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private List<Course> courses;
    private TextView courseNameEdit, courseCodeEdit;
    private String courseID;
    private Course editCourse;
    private ValueChecker valueChecker = new ValueChecker();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);

        courses =new LinkedList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        courseReference=firebaseDatabase.getReference("CourseList");

        courseNameEdit=(TextView)findViewById(R.id.courseNameEdit);
        courseCodeEdit=(TextView)findViewById(R.id.courseCodeEdit);



        Intent intent=getIntent();
        courseID=intent.getStringExtra("id");
        System.out.println(courseID);
        String courseName=intent.getStringExtra("courseName");
        String courseCode=intent.getStringExtra("courseCode");
        courseNameEdit.setText(courseName);
        courseCodeEdit.setText(courseCode);


        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Course course =child.getValue(Course.class);
                    course.setId(child.getKey());
                    courses.add(course);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    public boolean checkCourse1(){
//        for(Course course : courses){
//            if(course.getCourseName().equals(courseNameEdit.getText()) && course.getCourseCode().equals(courseCodeEdit.getText())){
//                editCourse= course;
//            }
//        }
//        return true;
//
//    }
    public boolean checkCourse() {
        String courseNameEdit1 = courseNameEdit.getText().toString();
        String courseCodeEdit1 = courseCodeEdit.getText().toString();
        for (Course course : courses) {
            if (valueChecker.findCourse(courseNameEdit1, courseCodeEdit1, course)) {
                editCourse = course;
            }
        }
        return true;
    }
    public void deleteMethod(View view){
        courseReference.child(courseID).setValue(null);
        finish();
    }
    public void editCourseMethod(View view){
        if(checkCourse()) {
            Intent intent = new Intent(getApplicationContext(), EditHomePage.class);
            intent.putExtra("editCourseName", editCourse.getCourseName());
            intent.putExtra("editCourseCode", editCourse.getCourseCode());
            intent.putExtra("afterCourseId", courseID);
            startActivity(intent);
        }
    }
}