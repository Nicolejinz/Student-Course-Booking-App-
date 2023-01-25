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

import com.example.booking.entity.Accounts;
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

public class CourseListActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private ListView listView;

    private List<Accounts> accounts=new LinkedList<>();
    private List<Course> courses;
    private List<String> studentId;
    private List<String> instructorId;
    private ListView courseListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_activity);
        firebaseDatabase = FirebaseDatabase.getInstance();
        courseReference = firebaseDatabase.getReference("CourseList");

        courseListView=findViewById(R.id.courseListView);
        listView=(ListView) findViewById(R.id.courseListView);

        studentId = new LinkedList<>();
        instructorId = new LinkedList<>();
        courses = new LinkedList<>();



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
                SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(),data,R.layout.course_list_view_layout,
                        new String[]{"id","courseCode","courseName"},new int[]{R.id.idTextView,R.id.courseCodeText,R.id.courseNameText});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView idTextView=(TextView)view.findViewById(R.id.idTextView);
                        TextView courseNameText=(TextView)view.findViewById(R.id.courseNameText);
                        TextView courseCodeText=(TextView)view.findViewById(R.id.courseCodeText);
                        Intent intent=new Intent(getApplicationContext(), CourseEdit.class);
                        intent.putExtra("id", idTextView.getText());
                        intent.putExtra("courseName", courseNameText.getText());
                        intent.putExtra("courseCode", courseCodeText.getText());
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