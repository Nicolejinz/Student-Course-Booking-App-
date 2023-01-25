package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

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

public class EditHomePage extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference courseReference;
    private List<Course> courses =new LinkedList<>();
    private EditText courseNameEditing, courseCodeEditing;
    private String courseID,editCourseName,editCourseCode;
    private String editingCourseName,editingCourseCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_home_page);
        firebaseDatabase=FirebaseDatabase.getInstance();
        courseReference=firebaseDatabase.getReference("CourseList");

        courseNameEditing=(EditText)findViewById(R.id.courseNameEditing);
        courseCodeEditing=(EditText)findViewById(R.id.courseCodeEditing);

        Intent intent=getIntent();
        editCourseCode=intent.getStringExtra("editCourseCode");
        editCourseName=intent.getStringExtra("editCourseName");
        courseID=intent.getStringExtra("afterCourseId");
        courseCodeEditing.setText(editCourseCode);
        courseNameEditing.setText(editCourseName);


        courseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Course course =child.getValue(Course.class);
                    course.setId(courseID);
                    courses.add(course);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        courseNameEditing.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editingCourseName=editable.toString();


            }
        });
        courseCodeEditing.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editingCourseCode=editable.toString();
            }
        });
    }
    public void editingMethod(View view){
        for(Course course:courses){
            if(course.getId().equals(courseID)){
                course.setCourseCode(courseCodeEditing.getText().toString());
                course.setCourseName(courseNameEditing.getText().toString());
                courseReference.child(courseID).setValue(course);
            }
        }
        finish();

    }
}