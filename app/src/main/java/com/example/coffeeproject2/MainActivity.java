package com.example.coffeeproject2;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coffeeproject2.database.AppDatabase;

import org.apache.log4j.chainsaw.Main;

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
        bRegister = (Button) findViewById(R.id.buttonReg);

        email = (EditText) findViewById(R.id.inMail);
        password = (EditText) findViewById(R.id.inPass);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this,MenuActivity.class));
                AppDatabase myAppDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "users").allowMainThreadQueries().build();
                mail = email.getText().toString();
                pass = password.getText().toString();
                list.add(myAppDatabase.userDao().loadUsersName(mail, pass).toString());
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).contains(mail)){
                        startActivity(new Intent(MainActivity.this,MenuActivity.class));
                        System.out.println(list.get(i));
                        Toast.makeText(MainActivity.this, "Welcome",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid mail or password",
                                Toast.LENGTH_LONG).show();
                        System.out.println("nein");
                    }

                }
            }

        });
    }


}

