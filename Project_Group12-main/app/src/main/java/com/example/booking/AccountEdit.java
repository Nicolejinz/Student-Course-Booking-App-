package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.entity.Accounts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class AccountEdit extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference accountReference;
    private List<Accounts> accounts;
    private TextView nameEdit, roleEdit;
    private String accountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);

        accounts=new LinkedList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        accountReference=firebaseDatabase.getReference("Accounts");

        nameEdit=(TextView)findViewById(R.id.nameEdit);
        roleEdit=(TextView)findViewById(R.id.roleEdit);

        Intent intent=getIntent();
        accountID=intent.getStringExtra("accountID");
        String accountName=intent.getStringExtra("accountName");
        String accountRole=intent.getStringExtra("accountRole");
        nameEdit.setText(accountName);
        roleEdit.setText(accountRole);

        accountReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();;
                for(DataSnapshot child: snapshot.getChildren()){
                    Accounts account= child.getValue(Accounts.class);
                    account.setId(child.getKey());
                    accounts.add(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void deleteAccountMethod(View view){
        accountReference.child(accountID).setValue(null);
        finish();
    }
}