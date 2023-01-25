package com.example.booking;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
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

public class SearchHomePage extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private ListView listView;
    private TextView courseCodeSearch,courseNameSearch,searchingCode,searchingName;
    private List<Course> courses =new LinkedList<>();
    private EditText searchText;
    private String searchingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home_page);
        firebaseDatabase = FirebaseDatabase.getInstance();
        courseReference = firebaseDatabase.getReference("CourseList");

        searchText=(EditText)findViewById(R.id.searchText);
        courseCodeSearch=(TextView)findViewById(R.id.courseCodeSearch);
        courseNameSearch=(TextView)findViewById(R.id.courseNameSearch);
        searchingCode=(TextView)findViewById(R.id.searchingCode);
        searchingName=(TextView)findViewById(R.id.searchingName);

        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Course course =child.getValue(Course.class);
                    courses.add(course);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchText.addTextChangedListener(new TextWatcher() {
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
    public void searchMethod(View view) {
        int x = 0;
        for (Course course : courses) {
            if (searchingText.equals(course.getCourseCode()) || searchingText.equals(course.getCourseName())) {
                System.out.println(searchingText.equals(course.getCourseName()));
                courseCodeSearch.setText("Course Code:");
                courseNameSearch.setText("Course Name:");
                System.out.println(course.getCourseName());
                System.out.println(course.getCourseCode());
                searchingCode.setText(course.getCourseCode());
                searchingName.setText(course.getCourseName());
                x = 1;
                break;
            }

        }
        if (x == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "This course is not exist!", Toast.LENGTH_SHORT);
            toast.show();
            courseCodeSearch.setText("Course Code:");
            courseNameSearch.setText("Course Name:");
            searchingCode.setText("");
            searchingName.setText("");
        }
    }
}
