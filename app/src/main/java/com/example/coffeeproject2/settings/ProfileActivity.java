package com.example.coffeeproject2.settings;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.ui.login.MainActivity;
import com.example.coffeeproject2.ui.plantation.PlantationActivity;
import com.example.coffeeproject2.ui.plantation.PlantationAddActivity;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    TextView name;
    TextView pass;
    Button logout;
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

        name = (TextView)findViewById(R.id.text_name);

        pass = (TextView)findViewById(R.id.text_password);

        logout = (Button)findViewById(R.id.button_logout);


        name.setText("Logged in as " +nameProfile);
        pass.setText("Password " +passProfile);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
    }
}
