package com.example.coffeeproject2.settings;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.AppDatabase;
import com.example.coffeeproject2.ui.login.MainActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView name;
    TextView pass;
    Button logout;
    Button delete;
    AppDatabase appDatabase;
    public static String nameProfile;
    public static String passProfile;

    MainActivity main = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "users").allowMainThreadQueries().build();

        name = (TextView) findViewById(R.id.text_name);

        pass = (TextView) findViewById(R.id.text_password);

        logout = (Button) findViewById(R.id.button_logout);

        delete = (Button) findViewById(R.id.button_delete);


        name.setText("Logged in as " + nameProfile);
        pass.setText("Password " + passProfile);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDatabase.userDao().deleteUser(nameProfile);
                Toast.makeText(ProfileActivity.this, "User deleted",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
    }
}
