package com.example.coffeeproject2;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button bLogin;
    Button bRegister;
    EditText email;
    EditText password;
    String mail;
    String pass;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = (Button) findViewById(R.id.buttonSign);
        bRegister = (Button)findViewById(R.id.buttonReg);

        email = (EditText)findViewById(R.id.inMail);
        password = (EditText)findViewById(R.id.inPass);


        pass = password.getText().toString();


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AppDatabase myAppDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "users").allowMainThreadQueries().build();
                mail = email.getText().toString();
                list.add(myAppDatabase.userDao().loadUsersName(mail).toString());
                for (int i = 0;i < list.size();i++){
                    System.out.println(list.get(i));
                }
                }

        });
    }


}

