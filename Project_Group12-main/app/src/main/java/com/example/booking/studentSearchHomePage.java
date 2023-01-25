package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.entity.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class studentSearchHomePage extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private EditText studentSearchingText;
    private TextView studentCourseCode,studentCourseName,studentCourseCodeSearched,studentCourseNameSearched;
    private List<Course> courses =new LinkedList<>();
    private String searchingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search_home_page);
        firebaseDatabase = FirebaseDatabase.getInstance();
        courseReference = firebaseDatabase.getReference("CourseList");
        studentSearchingText=(EditText) findViewById(R.id.studentSearchingText);
        studentCourseCode=(TextView)findViewById(R.id.studentCourseCode);
        studentCourseName=(TextView)findViewById(R.id.studentCourseName);
        studentCourseCodeSearched=(TextView)findViewById(R.id.studentCourseCodeSearched);
        studentCourseNameSearched=(TextView)findViewById(R.id.studentCourseNameSearched);

        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Course course=child.getValue(Course.class);
                    courses.add(course);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        studentSearchingText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchingText=editable.toString();
            }
        });
    }
    public void studentSearchCourseMethod(View view){
        int x = 0;
        for (Course course : courses) {
            if(searchingText.equals(course.getDay())){
                Intent intent=new Intent(getApplicationContext(),studentCourseListView.class);
                intent.putExtra("studentCourse",course);
                startActivity(intent);
                x=1;
            }
            if (searchingText.equals(course.getCourseCode()) || searchingText.equals(course.getCourseName())) {
                Intent intent=new Intent(getApplicationContext(),studentCourseListView.class);
                intent.putExtra("studentCourse",course);
                startActivity(intent);
                x = 1;
                break;
            }
        }
        if (x == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "This course is not exist!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}