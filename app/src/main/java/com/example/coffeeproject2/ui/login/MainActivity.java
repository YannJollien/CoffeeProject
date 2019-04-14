package com.example.coffeeproject2.ui.login;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Database;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.User;
import com.example.coffeeproject2.settings.ProfileActivity;
import com.example.coffeeproject2.settings.SettingsActivity;
import com.example.coffeeproject2.ui.menu.MenuActivity;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.log4j.chainsaw.Main;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String CHANNEL_ID = "my_channel_01";
    //channel and id for Notification
    NotificationManagerCompat notificationManagerCompat;
    int NOTIFICATION_ID = 234;
    Button bLogin;
    Button bRegister;
    EditText email;
    EditText password;
    String pass;
    String mail;
    String id;
    ArrayList<String> list = new ArrayList<String>();
    SettingsActivity settingsActivity;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = (Button) findViewById(R.id.buttonSign);
        bRegister = (Button) findViewById(R.id.buttonReg);

        email = (EditText) findViewById(R.id.inMail);
        password = (EditText) findViewById(R.id.inPass);

        ref = FirebaseDatabase.getInstance().getReference("user");

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this,MenuActivity.class));
                mail = email.getText().toString();
                pass = password.getText().toString();

                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
    }


    //Adding the notification method
    private void addNotification() {
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

        //Nothing in intent --> delete notification wehn pressed
        Intent notificationIntent = new Intent();
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("CoffeStorage Application")
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentText("Welcome " + mail);

        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}

