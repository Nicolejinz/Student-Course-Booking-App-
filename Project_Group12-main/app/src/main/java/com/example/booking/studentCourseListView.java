package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.entity.Course;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class studentCourseListView extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_list_view);
        firebaseDatabase=FirebaseDatabase.getInstance();
        courseReference=firebaseDatabase.getReference("CourseList");
        listView=(ListView)findViewById(R.id.studentListView);
        Intent intent=getIntent();
        Course viewCourse=(Course) intent.getSerializableExtra("studentCourse");
        List<Map<String,String>> data=new LinkedList<>();
        Map<String,String> dataMap=new HashMap<>();
        dataMap.put("courseCode", viewCourse.getCourseCode());
        dataMap.put("courseName", viewCourse.getCourseName());
        data.add(dataMap);
        SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(),data,R.layout.student_listview_activity,
                new String[]{"courseCode","courseName"},new int[]{R.id.studentViewCourseCode,R.id.studentViewCourseName});
        listView.setAdapter(adapter);


    }
}