package com.example.booking;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class Register extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference accountReference;
    private EditText editUserName,editPassword;
    private RadioGroup roleRadioGroup;
    private String name,password;
    private Role role;
    private int roleId=-1;
    private List<Accounts> accounts=new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editUserName=(EditText)findViewById(R.id.editUserName);
        editPassword=(EditText)findViewById(R.id.editPassword);
        roleRadioGroup=(RadioGroup)findViewById(R.id.roleRadioGroup);

        firebaseDatabase=FirebaseDatabase.getInstance();
        accountReference=firebaseDatabase.getReference("Accounts");

        accountReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Accounts account=child.getValue(Accounts.class);
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
        roleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                roleId=radioGroup.getCheckedRadioButtonId();
                if(roleId==R.id.studentBtn){
                    role=Role.STUDENT;
                }else if(roleId==R.id.instructorBtn){
                    role=Role.INSTRUCTOR;
                }else {
                    role=null;
                }
            }
        });
    }
    public void register(View view){
        if(checkValueEmpty()){
            Accounts account=new Accounts(name,password,role);
            accountReference.push().setValue(account);
            finish();

        }
    }
    private Toast toastShow(String text){
        Toast toast=Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        return toast;
    }
    public boolean checkValueEmpty(){
        if(name==null||name.equals("")){
            toastShow("User name cannot be empty.").show();
            return false;
        }
        for(Accounts account: accounts){
            if(account.getName().equals(name)){
                toastShow("This user name is exist, please change a name.").show();
                return false;
            }
        }
        if(password==null||password.equals("")){
            toastShow("Password cannot be empty.").show();
            return false;
        }
        if(roleId==-1){
            toastShow("You need to choose a role.").show();
            return false;
        }
        return true;
    }


}
