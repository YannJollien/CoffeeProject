package com.example.coffeeproject2.ui.login;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.User;
import com.example.coffeeproject2.ui.storage.StorageAddActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RegisterActivity extends AppCompatActivity{
    Button bSave;
    EditText editLastName;
    EditText editName;
    EditText editEmail;
    EditText editPassword;
    String lastName;
    String firstName;
    String email;
    String password;

    DatabaseReference databaseUser;

    ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Registration");

        databaseUser = FirebaseDatabase.getInstance().getReference("user");

        userList = new ArrayList<>();

        bSave = (Button) findViewById(R.id.button_save);

        editLastName = (EditText)findViewById(R.id.lNameRegister);
        editName = (EditText)findViewById(R.id.nameRegister);
        editEmail = (EditText)findViewById(R.id.mailRegister);
        editPassword = (EditText)findViewById(R.id.passRegister);

        lastName = editLastName.getText().toString();
        firstName = editName.getText().toString();
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editLastName.getText().toString().equals("") || editName.getText().toString().equals("") ||
                        editEmail.getText().toString().equals("") || editPassword.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    lastName = editLastName.getText().toString();
                    firstName = editName.getText().toString();
                    email = editEmail.getText().toString();
                    password = editPassword.getText().toString();

                    String id = databaseUser.push().getKey();

                    User user = new User(lastName,firstName,email,password);

                    databaseUser.child(id).setValue(user);
                    //storageViewModel.insert(recyclerView);
                    editLastName.setText("");
                    editName.setText("");
                    editEmail.setText("");
                    editPassword.setText("");
                    System.out.println(id);
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    Toast.makeText(RegisterActivity.this, "Saved",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

        databaseUser.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userList.clear();

                for (DataSnapshot storageSnapshot : dataSnapshot.getChildren()){
                    User user = storageSnapshot.getValue(User.class);
                    userList.add(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
