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

public class InstructorViewCourse extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private ListView listView;
    private String instructorId,instructorName;
    private String studentEnrolled,startTime,endTime,day;

    private List<Course> courses =new LinkedList<>();
    private List<String> studentId;
    private ListView courseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_view_course);
        firebaseDatabase = FirebaseDatabase.getInstance();
        courseReference = firebaseDatabase.getReference("CourseList");
        Intent intent=getIntent();
        instructorId=intent.getStringExtra("InstructorId");
        instructorName=intent.getStringExtra("InstructorName");


        listView=(ListView)findViewById(R.id.viewCourseList);

        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                List<Map<String,String>> data=new LinkedList<>();
                for(DataSnapshot child: snapshot.getChildren()){
                    Course course =child.getValue(Course.class);
                    course.setId(child.getKey());
                    courses.add(course);

                    Map<String,String> dataMap=new HashMap<>();
                    dataMap.put("courseCode", course.getCourseCode());
                    dataMap.put("id", course.getId());
                    dataMap.put("courseName", course.getCourseName());
                    data.add(dataMap);
                }
                SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(),data,R.layout.instructor_course_list,
                        new String[]{"id","courseCode","courseName"},new int[]{R.id.instructorCourseId,R.id.instructorCourseCode,R.id.instructorCourseName});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView idTextView=(TextView)view.findViewById(R.id.instructorCourseId);
                        TextView courseNameText=(TextView)view.findViewById(R.id.instructorCourseName);
                        TextView courseCodeText=(TextView)view.findViewById(R.id.instructorCourseCode);
                        for(Course course: courses){
                            if(course.getId().equals(idTextView.getText().toString())){
                                studentEnrolled=course.getStudentEnrolled();
                                startTime=course.getStartTime();
                                endTime=course.getEndTime();
                                day=course.getDay();
                            }
                        }
                        Intent intent=new Intent(getApplicationContext(), AssignCourse.class);
                        intent.putExtra("id", idTextView.getText());
                        intent.putExtra("courseName", courseNameText.getText());
                        intent.putExtra("courseCode", courseCodeText.getText());
                        intent.putExtra("InstructorId",instructorId);
                        intent.putExtra("InstructorName",instructorName);
                        intent.putExtra("studentEnrolled",studentEnrolled);
                        intent.putExtra("startTime",startTime);
                        intent.putExtra("endTime",endTime);
                        intent.putExtra("day",day);
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