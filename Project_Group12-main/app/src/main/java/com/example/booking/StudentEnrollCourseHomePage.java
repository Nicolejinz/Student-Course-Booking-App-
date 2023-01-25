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

public class StudentEnrollCourseHomePage extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private String studentName,studentId;
    private List<Course> courses=new LinkedList<>();
    private ListView listView;
    private Course coursePush=new Course();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enroll_course_home_page);
        firebaseDatabase=FirebaseDatabase.getInstance();
        courseReference=firebaseDatabase.getReference("CourseList");
        listView=(ListView)findViewById(R.id.studentEnrollCourseListView);
        Intent intent=getIntent();
        studentName=intent.getStringExtra("studentName");
        studentId=intent.getStringExtra("studentId");



        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                List<Map<String,String>> data=new LinkedList<>();
                for(DataSnapshot child:snapshot.getChildren()){
                    Course course = child.getValue(Course.class);
                    course.setId(child.getKey());
                    courses.add(course);

                    Map<String,String> dataMap=new HashMap<>();
                    dataMap.put("courseCode", course.getCourseCode());
                    dataMap.put("courseId", course.getId());
                    dataMap.put("courseName", course.getCourseName());
                    data.add(dataMap);
                }
                SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(),data,R.layout.student_enroll_course_list_view,
                        new String[]{"courseId","courseCode","courseName"},new int[]{R.id.enrollCourseId,R.id.enrollCourseCode,R.id.enrollCourseName});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView courseId=(TextView)view.findViewById(R.id.enrollCourseId);
                        TextView courseNameText=(TextView)view.findViewById(R.id.enrollCourseName);
                        TextView courseCodeText=(TextView)view.findViewById(R.id.enrollCourseCode);
                        for(Course course:courses){
                            if(courseId.getText().toString().equals(course.getId())){
                                coursePush=course;
                            }
                        }
                        Intent intent=new Intent(getApplicationContext(), EnrollCourse.class);
                        intent.putExtra("courseId", courseId.getText());
                        intent.putExtra("courseName", courseNameText.getText());
                        intent.putExtra("courseCode", courseCodeText.getText());
                        intent.putExtra("studentName",studentName);
                        intent.putExtra("course",coursePush);
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