package com.example.coffeeproject2.ui.login;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.AppDatabase;
import com.example.coffeeproject2.database.entity.User;



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
    AppDatabase myAppDatabase;

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

        myAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"users").allowMainThreadQueries().build();

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
                lastName = editLastName.getText().toString();
                firstName = editName.getText().toString();
                email = editEmail.getText().toString();
                password = editPassword.getText().toString();

                //create new User and add to DB
                User user = new User();
                //user.setUserId(id);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(password);

                myAppDatabase.userDao().insertUser(user);
                Toast.makeText(RegisterActivity.this, "Saved",
                        Toast.LENGTH_LONG).show();
                editLastName.setText("");
                editName.setText("");
                        editEmail.setText("");
                        editPassword.setText("");

            }
        });

    }
}
