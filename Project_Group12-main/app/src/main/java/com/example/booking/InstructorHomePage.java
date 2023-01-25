package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.entity.Accounts;
import com.example.booking.entity.Course;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class InstructorHomePage extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private ListView listView;
    private TextView AdminText;
    private EditText editUserName,editPassword;
    private List<Accounts> accounts=new LinkedList<>();
    private List<Course> courses;
    private List<String> studentId;
    private List<String> courseName;
    private List<String> courseCode;
    private TextView instructorText;
    private String instructorId,instructorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_home_page);
        firebaseDatabase = FirebaseDatabase.getInstance();
        courseReference = firebaseDatabase.getReference("CourseList");

        instructorText=(TextView)findViewById(R.id.welcomeInstructor);
        Intent intent=getIntent();
        Accounts account=(Accounts) intent.getSerializableExtra("Instructor");
        instructorId=intent.getStringExtra("InstructorId");
        instructorText.setText("Welcome "+"'"+account.getName()+"'! You are log in as a "+account.getRole()+"!");
        courses = new LinkedList<>();
        instructorId=account.getId();
        instructorName=account.getName();
    }

    public void viewCourse(View view){
        Intent intent=new Intent(getApplicationContext(),InstructorViewCourse.class);
        intent.putExtra("InstructorId",instructorId);
        intent.putExtra("InstructorName",instructorName);
        startActivity(intent);
    }
    public void searchCourse(View view){
        Intent intent=new Intent(getApplicationContext(),SearchHomePage.class);
        startActivity(intent);
    }
    public void checkEnrolledStudentList(View view){
        Intent intent=new Intent(getApplicationContext(), AssignedCourseListView.class);
        intent.putExtra("InstructorId",instructorId);
        intent.putExtra("InstructorName",instructorName);
        startActivity(intent);

    }

}