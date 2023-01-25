package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.entity.Accounts;
import com.example.booking.entity.Role;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class Login extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference accountReference;
    private EditText editUserName,editPassword;
    private String name,password;
    private List<Accounts> accounts=new LinkedList<>();
    private Accounts logInAccount;
    private String instructorId,studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUserName=(EditText)findViewById(R.id.editUserName);
        editPassword=(EditText)findViewById(R.id.editPassword);


        firebaseDatabase=FirebaseDatabase.getInstance();
        accountReference=firebaseDatabase.getReference("Accounts");

        accountReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Accounts account=child.getValue(Accounts.class);
                    account.setId(child.getKey());
                    accounts.add(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        editUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name=editable.toString();



            }
        });
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password=editable.toString();


            }
        });
    }
    public void register(View view){
        Intent intent=new Intent(getApplicationContext(),Register.class);
        startActivity(intent);
    }
    private Toast toastShow(String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        return toast;
    }
    public boolean checkValueValid() {
        if (name == null || name.equals("")) {
            toastShow("User name cannot be empty.").show();
            return false;
        }
        if (password == null || password.equals("")) {
            toastShow("Password cannot be empty.").show();
            return false;
        }
        for (Accounts account : accounts) {
            if (account.getName().equals(name)) {
                logInAccount=account;
            }
        }
        if(logInAccount==null||!logInAccount.getName().equals(name)){
            toastShow("User does not exist! Please try again").show();
            return false;
        }
        if (!logInAccount.getPassword().equals(password)) {
            toastShow("Incorrect password! Try again!").show();
            return false;
        }
        return true;
    }
    public void login(View view){
        if(checkValueValid()) {
            if (name.equals("admin") ) {
                Intent intent;
                intent = new Intent(getApplicationContext(), AdminHomePage.class);
                intent.putExtra("admin", logInAccount);
                startActivity(intent);
            } else {
                if(logInAccount.getRole().equals(Role.INSTRUCTOR) ){
                    Intent intent;
                    intent = new Intent(getApplicationContext(), InstructorHomePage.class);
                    intent.putExtra("Instructor", logInAccount);
                    instructorId=logInAccount.getId();
                    intent.putExtra("InstructorId",instructorId);
                    startActivity(intent);
                }if(logInAccount.getRole().equals(Role.STUDENT)){
                    Intent intent;
                    intent=new Intent(getApplicationContext(), StudentHomePage.class);
                    intent.putExtra("Student",logInAccount);
                    studentId=logInAccount.getId();
                    intent.putExtra("studentId",studentId);
                    startActivity(intent);
                }
            }
        }




    }
}