package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.entity.Accounts;

public class StudentHomePage extends AppCompatActivity {
    private TextView welcomeText;
    private String studentId;
    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        Intent intent=getIntent();
        Accounts account=(Accounts) intent.getSerializableExtra("Student");
        studentName=account.getName();
        studentId=intent.getStringExtra("studentId");
        welcomeText.setText("Welcome "+"'"+account.getName()+"'! You are log in as a "+account.getRole()+"!");
    }
    public void searchHomePage(View view){
        Intent intent=new Intent(getApplicationContext(),studentSearchHomePage.class);
        startActivity(intent);
    }
    public void enrollCourse(View view){
        Intent intent=new Intent(getApplicationContext(),StudentEnrollCourseHomePage.class);
        intent.putExtra("studentName",studentName);
        intent.putExtra("studentId",studentId);
        startActivity(intent);
    }
    public void checkEnrolledCourse(View view){
        Intent intent=new Intent(getApplicationContext(),EnrolledCourseListView.class);
        intent.putExtra("studentName",studentName);
        intent.putExtra("studentId",studentId);
        startActivity(intent);
    }


}