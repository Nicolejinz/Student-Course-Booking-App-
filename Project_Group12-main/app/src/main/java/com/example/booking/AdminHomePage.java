package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.LinkedList;
import java.util.List;

public class AdminHomePage extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference accountReference;
    private DatabaseReference courseReference;
    private ListView listView;
    private TextView AdminText;
    private EditText editUserName,editPassword;
    private List<Accounts> accounts=new LinkedList<>();
    private List<Course> courses;
    private List<String> studentId;
    private List<String> instructorId;
    private List<String> courseName;
    private List<String> courseCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        firebaseDatabase = FirebaseDatabase.getInstance();
        accountReference = firebaseDatabase.getReference("Accounts");
        courseReference = firebaseDatabase.getReference("CourseList");

        studentId = new LinkedList<>();
        instructorId = new LinkedList<>();
        courses = new LinkedList<>();

        AdminText = (TextView) findViewById(R.id.welcomeText);

        Intent intent = getIntent();
        Accounts account = (Accounts) intent.getSerializableExtra("admin");
        AdminText.setText("Welcome " + "'" + account.getName() + "'! You are log in as a " + account.getRole() + "!");

        accountReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void createCourse(View view){
        Intent intent=new Intent(getApplicationContext(),CreateCourse.class);
        startActivity(intent);
    }
    public void courseBtn(View view){
        Intent intent=new Intent(getApplicationContext(), CourseListActivity.class);
        startActivity(intent);
    }
    public void accountBtn(View view){
        Intent intent=new Intent(getApplicationContext(),AccountListActivity.class);
        startActivity(intent);

    }





//                courseLists.clear();
//                List<Map<String,String>> data=new LinkedList<>();
//                for(DataSnapshot child: snapshot.getChildren()){
//                    CourseList courseList=child.getValue(CourseList.class);
//                    courseLists.add(courseList);
//
//                    Map<String,String> dataMap=new HashMap<>();
//                    dataMap.put("courseCode",courseList.getCourseCode());
//                    dataMap.put("courseName", courseList.getCourseName());
//                    data.add(dataMap);
//                }
//                SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(),data,R.layout.course_list_view_layout,new String[]{"courseCode","courseName"},new int[]{R.id.courseCode,R.id.courseName});
//                listView.setAdapter(adapter);
//
//            }
//

//        courseListReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//            }
//        });



}