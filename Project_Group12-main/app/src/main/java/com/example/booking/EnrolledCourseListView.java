package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.entity.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EnrolledCourseListView extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private String studentName,studentId;
    private List<Course> courses=new LinkedList<>();
    private ListView listView;
    private List<String> studentList=new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled_course_list_view);
        firebaseDatabase=FirebaseDatabase.getInstance();
        courseReference=firebaseDatabase.getReference("CourseList");

        listView=(ListView)findViewById(R.id.enrolledCourseListView);

        Intent intent=getIntent();
        studentName=intent.getStringExtra("studentName");
        studentId=intent.getStringExtra("studentId");
        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                List<Map<String,String>> data=new LinkedList<>();
                for(DataSnapshot child:snapshot.getChildren()) {
                    Course course = child.getValue(Course.class);
                    course.setId(child.getKey());
                    courses.add(course);
                    if (course.getStudentEnrolled() != null) {
                        String[] strings=course.getStudentEnrolled().split(",");
                        studentList.clear();
                        for(int i=0;i<strings.length;i++) {
                            studentList.add(strings[i]);
                            if (studentList.contains(studentName)) {
                                Map<String, String> dataMap = new HashMap<>();
                                dataMap.put("courseCode", course.getCourseCode());
                                dataMap.put("courseId", course.getId());
                                dataMap.put("courseName", course.getCourseName());
                                data.add(dataMap);
                            }
                        }
                    }

                }
                SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(),data,R.layout.enrolled_course_list,
                        new String[]{"courseId","courseCode","courseName"},new int[]{R.id.enrolledCourseId,R.id.enrolledCourseCode,R.id.enrolledCourseName});
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}