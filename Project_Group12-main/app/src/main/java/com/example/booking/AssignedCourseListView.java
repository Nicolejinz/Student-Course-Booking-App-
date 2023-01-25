package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

public class AssignedCourseListView extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private ListView listView;
    private String instructorId,instructorName;
    private List<Course> courses =new LinkedList<>();
    private String studentEnrolled;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_course_list_view);

        firebaseDatabase=FirebaseDatabase.getInstance();
        courseReference=firebaseDatabase.getReference("CourseList");

        Intent intent=getIntent();
        instructorId=intent.getStringExtra("InstructorId");
        instructorName=intent.getStringExtra("InstructorName");

        listView=(ListView) findViewById(R.id.enrolledStudentList);

        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                List<Map<String,String>> data=new LinkedList<>();
                for(DataSnapshot child: snapshot.getChildren()){
                    Course course=child.getValue(Course.class);
                    course.setId(child.getKey());
                    courses.add(course);
                    if(course.getInstructorName().equals(instructorName)){
                        Map<String,String> dataMap=new HashMap<>();
                        dataMap.put("courseCode", course.getCourseCode());
                        dataMap.put("id", course.getId());
                        dataMap.put("courseName", course.getCourseName());
                        data.add(dataMap);
                        }
                    }
                SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(),data,R.layout.assigned_course_list,
                        new String[]{"id","courseCode","courseName"},new int[]{R.id.assignedCourseId,R.id.assignedCourseCode,R.id.assignedCourseName});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView idTextView=(TextView)view.findViewById(R.id.assignedCourseId);
                        Intent intent=new Intent(getApplicationContext(),EnrolledStudentListView.class);
                        for(Course course: courses){
                            if(course.getId().equals(idTextView.getText().toString())){
                                studentEnrolled=course.getStudentEnrolled();
                            }
                        }
                        intent.putExtra("studentEnrolled",studentEnrolled);
                        startActivity(intent);

                    }
                });

                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}