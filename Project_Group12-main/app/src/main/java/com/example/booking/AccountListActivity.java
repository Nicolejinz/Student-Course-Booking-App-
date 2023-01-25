package com.example.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking.entity.Accounts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AccountListActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference accountReference;
    private ListView listView;
    private TextView AdminText;
    private EditText editUserName,editPassword;
    private List<Accounts> accounts;
    private ListView accountListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        firebaseDatabase = FirebaseDatabase.getInstance();
        accountReference = firebaseDatabase.getReference("Accounts");

        accountListView=(ListView) findViewById(R.id.accountListView);
        listView=(ListView) findViewById(R.id.accountListView);


        accounts = new LinkedList<>();

        accountReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                List<Map<String,String>> accountData=new LinkedList<>();
                for(DataSnapshot child: snapshot.getChildren()){
                    Accounts account=child.getValue(Accounts.class);
                    account.setId(child.getKey());
                    accounts.add(account);

                    Map<String,String> dataMap=new HashMap<>();
                    dataMap.put("accountName",account.getName());
                    dataMap.put("accountID", account.getId());
                    dataMap.put("accountRole", account.getRole().toString());
                    accountData.add(dataMap);
                }
                SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(),accountData,R.layout.account_list_view_layout,
                        new String[]{"accountID","accountName","accountRole"},new int[]{R.id.idAccountText,R.id.nameText,R.id.roleText});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView idAccountText=(TextView)view.findViewById(R.id.idAccountText);
                        TextView accountNameText=(TextView)view.findViewById(R.id.nameText);
                        TextView accountRoleText=(TextView)view.findViewById(R.id.roleText);
                        Intent intent=new Intent(getApplicationContext(), AccountEdit.class);
                        intent.putExtra("accountID", idAccountText.getText());
                        intent.putExtra("accountName", accountNameText.getText());
                        intent.putExtra("accountRole", accountRoleText.getText());
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