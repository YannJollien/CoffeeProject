package com.example.coffeeproject2.ui.login;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coffeeproject2.adapter.StorageAdapter;
import com.example.coffeeproject2.settings.ProfileActivity;
import com.example.coffeeproject2.ui.menu.MenuActivity;
import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.AppDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    NotificationManagerCompat notificationManagerCompat;
    int NOTIFICATION_ID = 234;
    private static String CHANNEL_ID = "my_channel_01";


    Button bLogin;
    Button bRegister;
    EditText email;
    EditText password;
    String pass;
    String mail;
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
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                        System.out.println(list.get(i));
                        ProfileActivity.nameProfile = mail;
                        ProfileActivity.passProfile = pass;
                        addNotification();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid mail or password",
                                Toast.LENGTH_LONG).show();
                        System.out.println("nein");
                    }

                }


            }

        });

    }

    private void addNotification(){
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("CoffeStorage Application")
                .setContentText("Welcome "+mail);

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}

