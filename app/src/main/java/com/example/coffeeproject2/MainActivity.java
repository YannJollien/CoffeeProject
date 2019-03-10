package com.example.coffeeproject2;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button bLogin;
    Button bRegister;
    AppDatabase myAppDatabase;
    EditText email;
    EditText password;
    String mail;
    String pass;
    String users [];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = (Button) findViewById(R.id.buttonSign);
        bRegister = (Button)findViewById(R.id.buttonReg);

        email = (EditText)findViewById(R.id.inMail);
        password = (EditText)findViewById(R.id.inPass);

        mail = email.getText().toString();
        pass = password.getText().toString();

        myAppDatabase = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"users").allowMainThreadQueries().build();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //startActivity(new Intent(MainActivity.this, MenuActivity.class));
                myAppDatabase.userDao().loadAllUsersWithMail(mail);
                User user[] = myAppDatabase.userDao().loadAllUsersWithMail(mail);
                int size = user.length;
                Log.d("User", user[1].email);
                Log.d("Uses lend",String.valueOf(size));
                for (int i = 0; i < myAppDatabase.userDao().loadAllUsersWithMail(mail).length;i++){
                    if ( myAppDatabase.userDao().loadAllUsersWithMail(mail)[i].equals(mail)){
                        System.out.println("Exists");
                    } else {
                        System.out.println("Nop");
                    }
                }
            }
        });
    }


}

