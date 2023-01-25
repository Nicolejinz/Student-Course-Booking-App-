package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EnrolledStudentListView extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private String studentEnrolled;
    private List<String> enrolledStudentList = new LinkedList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled_student_list_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        courseReference = firebaseDatabase.getReference("CourseList");
        listView = (ListView) findViewById(R.id.enrolledStudentListView);
        Intent intent = getIntent();
        studentEnrolled = intent.getStringExtra("studentEnrolled");
        if (studentEnrolled != null) {
            String[] strings = studentEnrolled.split(",");
            for (int i = 0; i < strings.length; i++) {
                if (!strings[i].equals("")) {
                    enrolledStudentList.add(strings[i]);
                }
            }
            List<Map<String, String>> enrolledStudent = new LinkedList<>();

            for (int i = 0; i < enrolledStudentList.size(); i++) {
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("StudentName", enrolledStudentList.get(i));
                enrolledStudent.add(dataMap);
            }
            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), enrolledStudent, R.layout.enrolled_student_list,
                    new String[]{"StudentName"}, new int[]{R.id.EnrolledstudentName});
            listView.setAdapter(adapter);
        }
    }
}